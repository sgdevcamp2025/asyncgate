package com.asyncgate.apigatewayserver.config;

public class CorsConfig {
    public static final String ALLOWED_ORIGIN = "http://localhost:5173";
    public static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    public static final String ALLOWED_HEADERS = "*";
    public static final String ALLOW_CREDENTIALS = "true";
    public static final String OPTIONS_HEADERS = "OPTIONS";
}
