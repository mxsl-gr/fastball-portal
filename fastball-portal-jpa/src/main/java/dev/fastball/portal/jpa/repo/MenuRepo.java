package dev.fastball.portal.jpa.repo;

import dev.fastball.portal.jpa.entity.JpaMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<JpaMenuEntity, Long> {

    JpaMenuEntity findByCode(String code);
}
