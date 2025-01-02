package dev.fastball.portal.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "fastball.security")
public class FastballPortalProperties {

    private FastballPortalAdminProperties admin = new FastballPortalAdminProperties();

    private String[] anonymousPath;

    public String[] getAnonymousPath() {
        return anonymousPath != null ? anonymousPath : new String[0];
    }
}
