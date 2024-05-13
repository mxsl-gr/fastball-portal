package dev.fastball.portal.jpa.service;

import dev.fastball.core.config.FastballConfig;
import dev.fastball.core.config.Menu;
import dev.fastball.core.utils.YamlUtils;
import dev.fastball.portal.core.dict.PermissionType;
import dev.fastball.portal.core.exception.FastballPortalException;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.service.FastballPortalInitService;
import dev.fastball.portal.jpa.entity.JpaMenuEntity;
import dev.fastball.portal.jpa.entity.JpaPermissionEntity;
import dev.fastball.portal.jpa.repo.MenuRepo;
import dev.fastball.portal.jpa.repo.PermissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaFastballPortalInitService implements FastballPortalInitService {

    private final PermissionRepo permissionRepo;
    private final MenuRepo menuRepo;

    @Override
    public void initMenusAndMenuPermissions() {
        ResourceLoader resourceResolver = new PathMatchingResourcePatternResolver();
        Resource menuResource = resourceResolver.getResource("classpath:/fastball-config.yml");
        try (InputStream inputStream = menuResource.getInputStream()) {
            FastballConfig fastballConfig = YamlUtils.fromYaml(inputStream, FastballConfig.class);
            if (fastballConfig == null || fastballConfig.getMenus() == null) {
                return;
            }
            for (Map.Entry<String, Menu> menu : fastballConfig.getMenus().entrySet()) {
                initMenuAndPermission(Collections.singletonList(menu.getKey()), menu.getValue(), null, null);
            }
        } catch (IOException e) {
            throw new FastballPortalException(e);
        }
    }

    private void initMenuAndPermission(List<String> menuKeys, Menu menuInfo, JpaMenuEntity parentMenu, JpaPermissionEntity parentPermission) {
        String menuCode = String.join("-", menuKeys);
        JpaMenuEntity menuEntity = menuRepo.findByCode(menuCode);
        if (menuEntity == null) {
            String menuPath = "/" + String.join("/", menuKeys);
            menuEntity = new JpaMenuEntity();
            menuEntity.setCode(menuCode);
            menuEntity.setPath(menuPath);
        }
        if (parentMenu != null) {
            menuEntity.setParentId(parentMenu.getId());
        }
        menuEntity.setName(menuInfo.getTitle());
        menuEntity.setDescription(menuInfo.getDescription());
        menuEntity.setParams(menuInfo.getParams());
        menuRepo.save(menuEntity);
        JpaPermissionEntity permission = initMenuPermission(menuEntity, parentPermission);
        if (menuInfo.getMenus() == null) {
            return;
        }
        for (Map.Entry<String, Menu> subMenu : menuInfo.getMenus().entrySet()) {
            List<String> parentMenuKeys = new ArrayList<>(menuKeys);
            parentMenuKeys.add(subMenu.getKey());
            initMenuAndPermission(parentMenuKeys, subMenu.getValue(), menuEntity, permission);
        }
    }

    private JpaPermissionEntity initMenuPermission(JpaMenuEntity menu, JpaPermissionEntity parentPermission) {
        JpaPermissionEntity permission = permissionRepo.findByTypeAndCode(PermissionType.Menu, menu.getCode());
        if (permission == null) {
            permission = new JpaPermissionEntity();
        }
        permission.setType(PermissionType.Menu);
        permission.setName(menu.getName());
        permission.setCode(menu.getCode());
        permission.setTarget(menu.getId().toString());
        if (parentPermission != null) {
            permission.setParentId(parentPermission.getId());
        }
        permissionRepo.save(permission);
        return permission;
    }

    @Override
    public void initPermissions() {

    }
}
