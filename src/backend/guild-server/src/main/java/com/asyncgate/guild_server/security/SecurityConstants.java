package com.asyncgate.guild_server.security;

public class SecurityConstants {
    public static final String[] PUBLIC_ENDPOINTS = {
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/index.css",
            "/swagger-ui/index.css",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/favicon-32x32.png",
            "/swagger-ui/favicon-16x16.png",
            "/swagger-ui/index.css",
            "/v3/api-docs/swagger-config",
            "/error",
            "/health",
            "/actuator/info",
            "/direct/open/**"
    };
}
