package com.scaffolding.optimization.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
public class RoleConfig {

    public static final String ROLE_PREFIX = "THE_GRANTED_ROLE_AUTHORITY_";
    
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(ROLE_PREFIX); // Remove the ROLE_ prefix
    }
}
