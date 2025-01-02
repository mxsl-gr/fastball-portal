package dev.fastball.portal.jpa.repo;

import dev.fastball.portal.jpa.entity.JpaRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<JpaRoleEntity, Long> {
    JpaRoleEntity findByCode(String code);
}
