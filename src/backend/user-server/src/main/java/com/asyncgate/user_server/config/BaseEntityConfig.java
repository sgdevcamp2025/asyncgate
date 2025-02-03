package com.asyncgate.user_server.config;

import com.asyncgate.user_server.security.info.CustomUserPrincipal;
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

    @Bean("user-auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("AnonymousNULL");
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserPrincipal) {
                return Optional.of(((CustomUserPrincipal) principal).getId());
            }

            return Optional.of("AnonymousNOT_TYPE");
        };
    }

}