package dev.fastball.portal.core.service;

import dev.fastball.portal.core.model.context.Permission;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public interface FastballPortalInitService extends InitializingBean {

    void initMenusAndMenuPermissions();

    void initPermissions();

    @Override
    default void afterPropertiesSet() {
        initMenusAndMenuPermissions();
    }
}
