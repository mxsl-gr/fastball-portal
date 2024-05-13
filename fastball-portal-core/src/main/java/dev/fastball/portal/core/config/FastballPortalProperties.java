package dev.fastball.portal.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastball.security")
public class FastballPortalProperties {

    private String[] anonymousPath;

    public void setAnonymousPath(String[] anonymousPath) {
        this.anonymousPath = anonymousPath;
    }

    public String[] getAnonymousPath() {
        return anonymousPath != null ? anonymousPath : new String[0];
    }
}
