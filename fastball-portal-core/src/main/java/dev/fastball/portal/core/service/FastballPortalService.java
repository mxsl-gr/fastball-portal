package dev.fastball.portal.core.service;

import dev.fastball.portal.core.dict.PermissionType;
import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.model.RegisterUser;
import dev.fastball.portal.core.model.context.Menu;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.model.context.Role;
import dev.fastball.portal.core.model.context.User;
import dev.fastball.portal.core.model.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public interface FastballPortalService {

    User registerUser(RegisterUser user);

    void changePassword(Long userId, String password, String newPassword);

    boolean resetPasswordByUserId(Long userId, String password);

    boolean resetPasswordByUserName(String username, String password);

    boolean resetPasswordByMobile(String mobile, String password);

    UserStatus getUserStatus(Long userId);

    boolean enableUser(Long userId);

    boolean disableUser(Long userId);

    void saveUserRoles(Long userId, Collection<Long> roleIds);

    void saveRolePermissions(Long roleId, Collection<Long> permissionIds);

    List<Permission> getAllPermissions();

    List<Role> getAllRole();

    UserEntity loadAccountByUsername(String username);

    User loadByUsername(String username);

    User loadByMobile(String mobile);

    User loadByWxOpenId(String wxOpenId);

    void bindWxOpenId(Long userId, String wxOpenId);

    List<Permission> getUserPermission(Long userId);

    List<Permission> getUserPermission(Long userId, PermissionType permissionType);

    List<Menu> getUserMenu(Long userId);

    List<Role> getUserRole(Long userId);
}
