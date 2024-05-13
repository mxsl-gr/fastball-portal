package dev.fastball.portal.core.config;

import dev.fastball.portal.core.service.FastballPortalMessageService;
import dev.fastball.portal.core.service.support.DefaultFastballPortalMessageService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class FastballPortalConfiguration {

    @Bean
    @ConditionalOnMissingBean(FastballPortalMessageService.class)
    public FastballPortalMessageService fastballPortalMessageService() {
        return new DefaultFastballPortalMessageService();
    }

}
