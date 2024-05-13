package dev.fastball.portal.jpa.ui;

import dev.fastball.core.annotation.RecordAction;
import dev.fastball.core.annotation.UIComponent;
import dev.fastball.portal.core.model.context.Role;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.entity.JpaUserEntity;
import dev.fastball.portal.jpa.model.UserRoleModel;
import dev.fastball.ui.components.form.VariableForm;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@UIComponent
@RequiredArgsConstructor
public class UserRoleForm implements VariableForm<UserRoleModel, JpaUserEntity> {
    private final FastballPortalService portalService;

    @Override
    public UserRoleModel loadData(JpaUserEntity user) {
        UserRoleModel staffRole = new UserRoleModel();
        staffRole.setUserId(user.getId());
        staffRole.setRoles(portalService.getUserRole(user.getId()).stream().map(Role::getId).collect(Collectors.toList()));
        return staffRole;
    }

    @RecordAction(name = "提交")
    public void save(UserRoleModel staffRoleModel) {
        portalService.saveUserRoles(staffRoleModel.getUserId(), staffRoleModel.getRoles());
    }
}
