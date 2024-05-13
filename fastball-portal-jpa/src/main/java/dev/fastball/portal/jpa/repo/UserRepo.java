package dev.fastball.portal.jpa.repo;

import dev.fastball.portal.jpa.entity.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<JpaUserEntity, Long> {

    JpaUserEntity findByUsername(String username);

    JpaUserEntity findByMobile(String mobile);

    JpaUserEntity findByWxOpenId(String wxOpenId);
}
