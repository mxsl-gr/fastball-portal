package dev.fastball.portal.jpa.config;

import dev.fastball.portal.core.service.FastballPortalInitService;
import dev.fastball.portal.jpa.entity.JpaUserEntity;
import dev.fastball.portal.jpa.repo.MenuRepo;
import dev.fastball.portal.jpa.repo.PermissionRepo;
import dev.fastball.portal.jpa.repo.RoleRepo;
import dev.fastball.portal.jpa.service.JpaFastballPortalInitService;
import dev.fastball.portal.jpa.service.JpaFastballPortalService;
import dev.fastball.portal.jpa.repo.UserRepo;
import dev.fastball.portal.core.service.FastballPortalService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfiguration
@EntityScan(basePackageClasses = JpaUserEntity.class)
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
@ComponentScan(basePackages = "dev.fastball.portal.jpa")
public class JpaFastballSecurityConfiguration {
    @Bean
    public FastballPortalService fastballPortalService(UserRepo fastballUserRepo, PermissionRepo permissionRepo, RoleRepo roleRepo, MenuRepo menuRepo, PasswordEncoder passwordEncoder) {
        return new JpaFastballPortalService(fastballUserRepo, permissionRepo, roleRepo, menuRepo, passwordEncoder);
    }

    @Bean
    public FastballPortalInitService fastballPortalInitService(PermissionRepo permissionRepo, MenuRepo menuRepo) {
        return new JpaFastballPortalInitService(permissionRepo, menuRepo);
    }
}
