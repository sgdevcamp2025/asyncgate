package com.asyncgate.guild_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class BaseEntityConfig {

    @Bean("guild-auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("AnonymousNULL");
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return Optional.of((String) principal);
            }

            return Optional.of("AnonymousNOT_TYPE");
        };
    }

}
