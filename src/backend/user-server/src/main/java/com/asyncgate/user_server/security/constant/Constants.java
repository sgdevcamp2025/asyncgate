package com.asyncgate.user_server.security.constant;

import java.util.List;

public class Constants {

    // JWT
    public static String MEMBER_ID_ATTRIBUTE_NAME = "MEMBER_ID";
    public static String MEMBER_ID_CLAIM_NAME = "mid";

    // HEADER
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";


    /**
     * 인증이 필요 없는 URL
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Authentication/Authorization
            "/connection-test",
            "/authentication-code",
            "/sign-up",
            "/sign-in",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**"
    );

    /**
     * Swagger 에서 사용하는 URL
     */
    public static List<String> SWAGGER_URLS = List.of(
            "/api-docs.html",
            "/api-docs",
            "/swagger-ui",
            "/v3"
    );

    /**
     * 사용자 URL
     */
    public static List<String> USER_URLS = List.of(
            "/v1/**"
    );
}