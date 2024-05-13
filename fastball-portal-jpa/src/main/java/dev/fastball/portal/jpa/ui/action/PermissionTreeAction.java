package dev.fastball.portal.jpa.ui.action;

import dev.fastball.core.annotation.UIComponent;
import dev.fastball.core.component.LookupActionParam;
import dev.fastball.core.component.TreeLookupAction;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.model.PermissionDTO;
import lombok.RequiredArgsConstructor;

import java.util.*;

@UIComponent
@RequiredArgsConstructor
public class PermissionTreeAction implements TreeLookupAction<PermissionDTO, Object> {
    private final FastballPortalService portalService;

    @Override
    public Collection<PermissionDTO> loadLookupItems(LookupActionParam<Object> param) {
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
        return permissionList;
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
