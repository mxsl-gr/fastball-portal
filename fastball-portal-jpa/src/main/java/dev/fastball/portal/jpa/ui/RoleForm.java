package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.RecordAction;
import dev.fastball.core.annotation.UIComponent;
import dev.fastball.portal.jpa.entity.JpaRoleEntity;
import dev.fastball.portal.jpa.model.RoleDTO;
import dev.fastball.portal.jpa.repo.PermissionRepo;
import dev.fastball.portal.jpa.repo.RoleRepo;
import dev.fastball.ui.components.form.Form;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@UIComponent
@RequiredArgsConstructor
public class RoleForm implements Form<RoleDTO> {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    @RecordAction(name = "提交")
    public void submit(RoleDTO role) {
        roleRepo.save(convertEntity(role));
    }

    private JpaRoleEntity convertEntity(RoleDTO role) {
        JpaRoleEntity roleEntity = new JpaRoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setCode(role.getCode());
        roleEntity.setName(role.getName());
        roleEntity.setDescription(role.getDescription());
        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
            roleEntity.setPermissions(permissionRepo.findAllById(role.getPermissions()));
        } else {
            roleEntity.setPermissions(Collections.emptyList());
        }
        return roleEntity;
    }
}