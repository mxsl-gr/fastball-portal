package dev.fastball.portal.core.service;

import org.springframework.beans.factory.InitializingBean;

public interface FastballPortalInitService extends InitializingBean {

    void initMenusAndMenuPermissions();

    void initPermissions();

    @Override
    default void afterPropertiesSet() {
        initMenusAndMenuPermissions();
    }
}
