package dev.fastball.portal.core.context;

import dev.fastball.portal.core.exception.UnLoginException;
import dev.fastball.portal.core.model.context.Permission;
import dev.fastball.portal.core.model.context.User;
import dev.fastball.portal.core.service.FastballPortalService;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PortalContext implements ApplicationContextAware {

    private static FastballPortalService fastballPortalService;

    public static Optional<User> currentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
            return Optional.ofNullable(fastballPortalService.loadByUsername(username));
        }
        return Optional.ofNullable(fastballPortalService.loadByUsername((String) authentication.getPrincipal()));

    }

    public static User currentUser() {
        return currentUserOptional().orElseThrow(UnLoginException::new);
    }

    public static boolean hasPermission(String permissionCode) {
        return userPermissionCodeSet(currentUser().getId()).contains(permissionCode);
    }

    @Cacheable("current_user_permission_set")
    private static Set<String> userPermissionCodeSet(Long userId) {
        return fastballPortalService.getUserPermission(userId).stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        fastballPortalService = applicationContext.getBean(FastballPortalService.class);
    }
}
