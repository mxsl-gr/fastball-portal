package dev.fastball.portal.core.config;

import dev.fastball.portal.core.FastballPortalConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Geng Rong
 */
@Getter
@Setter
public class FastballPortalAdminProperties {
    private Boolean initAdmin = false;

    private String defaultUsername = FastballPortalConstants.ADMIN_DEFAULT_USERNAME;

    private String defaultPassword = FastballPortalConstants.ADMIN_DEFAULT_PASSWORD;

    private String defaultMobile = FastballPortalConstants.ADMIN_DEFAULT_MOBILE;

    private String adminRoleName = FastballPortalConstants.ADMIN_ROLE_NAME;

    private String adminRoleCode = FastballPortalConstants.ADMIN_ROLE_CODE;

    private String adminRoleDescription = FastballPortalConstants.ADMIN_ROLE_DESCRIPTION;
}
