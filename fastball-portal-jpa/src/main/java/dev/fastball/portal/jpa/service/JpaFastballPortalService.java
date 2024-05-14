package dev.fastball.portal.jpa.service;

import dev.fastball.core.exception.BusinessException;
import dev.fastball.portal.core.dict.PermissionType;
import dev.fastball.portal.core.dict.UserStatus;
import dev.fastball.portal.core.exception.RoleNotFoundException;
import dev.fastball.portal.core.exception.UserNotFoundException;
import dev.fastball.portal.core.model.CurrentUser;
import dev.fastball.portal.core.model.RegisterUser;
import dev.fastball.portal.core.model.context.Menu;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.model.context.Role;
import dev.fastball.portal.core.model.context.User;
import dev.fastball.portal.core.model.entity.UserEntity;
import dev.fastball.portal.core.service.FastballPortalService;
import dev.fastball.portal.jpa.entity.JpaRoleEntity;
import dev.fastball.portal.jpa.entity.JpaUserEntity;
import dev.fastball.portal.jpa.repo.MenuRepo;
import dev.fastball.portal.jpa.repo.PermissionRepo;
import dev.fastball.portal.jpa.repo.RoleRepo;
import dev.fastball.portal.jpa.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaFastballPortalService implements FastballPortalService {

    private final UserRepo userRepo;
    private final PermissionRepo permissionRepo;
    private final RoleRepo roleRepo;
    private final MenuRepo menuRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity loadAccountByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserEntity registerUser(RegisterUser user) {
        JpaUserEntity userEntity = new JpaUserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setNickname(user.getNickname());
        userEntity.setMobile(user.getMobile());
        userEntity.setStatus(UserStatus.Enabled);
        if (user.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.save(userEntity);
        return userEntity;
    }

    @Override
    public void changePassword(Long userId, String password, String newPassword) {
        Optional<JpaUserEntity> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return;
        }
        JpaUserEntity user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public boolean resetPasswordByUserId(Long userId, String password) {
        Optional<JpaUserEntity> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return false;
        }
        JpaUserEntity user = userOptional.get();
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean resetPasswordByUserName(String username, String password) {
        JpaUserEntity user = userRepo.findByUsername(username);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean resetPasswordByMobile(String mobile, String password) {
        JpaUserEntity user = userRepo.findByMobile(mobile);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
        return true;
    }

    @Override
    public UserStatus getUserStatus(Long userId) throws UserNotFoundException {
        Optional<JpaUserEntity> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        return userOptional.get().getStatus();
    }

    @Override
    public boolean enableUser(Long userId) {
        Optional<JpaUserEntity> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return false;
        }
        JpaUserEntity user = userOptional.get();
        user.setStatus(UserStatus.Enabled);
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean disableUser(Long userId) {
        Optional<JpaUserEntity> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return false;
        }
        JpaUserEntity user = userOptional.get();
        user.setStatus(UserStatus.Disabled);
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
        return true;
    }

    @Override
    public void saveUserRoles(Long userId, Collection<Long> roleIds) {
        JpaUserEntity user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setRoles(roleRepo.findAllById(roleIds));
        user.setNickname(user.getNickname());
        user.setLastUpdatedAt(new Date());
        userRepo.save(user);
    }

    @Override
    public void saveRolePermissions(Long roleId, Collection<Long> permissionIds) {
        JpaRoleEntity role = roleRepo.findById(roleId).orElseThrow(RoleNotFoundException::new);
        role.setPermissions(permissionRepo.findAllById(permissionIds));
        roleRepo.save(role);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepo.findAll().stream().map(p -> (Permission) p).collect(Collectors.toList());
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepo.findAll().stream().map(p -> (Role) p).collect(Collectors.toList());
    }

    @Override
    public CurrentUser loadByUsername(String username) {
        UserEntity account = loadAccountByUsername(username);
        if (account == null) {
            return null;
        }
        CurrentUser currentUser = new CurrentUser();
        BeanUtils.copyProperties(account, currentUser);
        return currentUser;
    }

    @Override
    public UserEntity loadByMobile(String mobile) {
        return userRepo.findByMobile(mobile);
    }

    @Override
    public User loadByWxOpenId(String wxOpenId) {
        return userRepo.findByWxOpenId(wxOpenId);
    }

    @Override
    public void bindWxOpenId(Long userId, String wxOpenId) {
        Optional<JpaUserEntity> userEntityOptional = userRepo.findById(userId);
        if (userEntityOptional.isPresent()) {
            JpaUserEntity userEntity = userEntityOptional.get();
            userEntity.setWxOpenId(wxOpenId);
            userRepo.save(userEntity);
        }
    }

    @Override
    public List<Permission> getUserPermission(Long userId) {
        return permissionRepo.findByUserId(userId).stream().map(p -> (Permission) p).collect(Collectors.toList());
    }

    @Override
    public List<Permission> getUserPermission(Long userId, PermissionType permissionType) {
        return permissionRepo.findByUserIdAndType(userId, permissionType.toString()).stream().map(p -> (Permission) p).collect(Collectors.toList());
    }

    @Override
    public List<Menu> getUserMenu(Long userId) {
        List<Long> menuIdList = getUserPermission(userId, PermissionType.Menu).stream().map(Permission::getTarget).filter(Objects::nonNull).mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
        return menuRepo.findAllById(menuIdList).stream().map(menu -> (Menu) menu).collect(Collectors.toList());
    }

    @Override
    public List<Role> getUserRole(Long userId) {
        JpaUserEntity user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getRoles().stream().map(role -> (Role) role).collect(Collectors.toList());
    }
}
