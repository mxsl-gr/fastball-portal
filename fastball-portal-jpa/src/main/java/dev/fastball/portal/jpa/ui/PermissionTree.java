package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.UIComponent;
import dev.fastball.core.component.DataResult;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.model.PermissionDTO;
import dev.fastball.ui.components.tree.Tree;
import dev.fastball.ui.components.tree.config.TreeConfig;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UIComponent
@TreeConfig(titleField = "name", childrenField = "subPermissions")
@RequiredArgsConstructor
public class PermissionTree implements Tree<PermissionDTO> {

    private final FastballPortalService portalService;

    @Override
    public DataResult<PermissionDTO> loadData() {
        Map<Long, List<PermissionDTO>> subPermissionMap = new HashMap<>();
        List<PermissionDTO> permissionList = new ArrayList<>();
        portalService.getAllPermissions().forEach(permission -> {
            PermissionDTO permissionDTO = this.convert(permission);
            if (permission.getParentId() != null) {
                List<PermissionDTO> subPermissions = subPermissionMap.computeIfAbsent(permission.getParentId(), pId -> new ArrayList<>());
                subPermissions.add(permissionDTO);
            } else {
                permissionList.add(permissionDTO);
            }
            permissionDTO.setSubPermissions(subPermissionMap.computeIfAbsent(permission.getId(), pId -> new ArrayList<>()));
        });
        return DataResult.build(permissionList);
    }

    private PermissionDTO convert(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setCode(permission.getCode());
        permissionDTO.setName(permission.getName());
        permissionDTO.setType(permission.getType());
        permissionDTO.setTarget(permission.getTarget());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }
}
