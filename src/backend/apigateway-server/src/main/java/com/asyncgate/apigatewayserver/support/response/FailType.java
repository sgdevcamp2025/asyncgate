package com.asyncgate.apigatewayserver.support.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailType {

    // -------------------------------------------    Authorization    -------------------------------------------
    AUTHORIZATION_MISSING_HEADER(HttpStatus.UNAUTHORIZED, "Auth_4011", "Authorization 헤더가 존재하지 않습니다."),
    AUTHORIZATION_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "Auth_4012", "Authorization 헤더의 형식이 잘못되었습니다."),

    // -------------------------------------------         JWT         -------------------------------------------
    JWT_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "Jwt_4011", "JWT 서명이 유효하지 않습니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Jwt_4012", "JWT 토큰이 만료되었습니다."),
    JWT_MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "Jwt_4013", "잘못된 형식의 JWT 토큰입니다."),
    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "Jwt_4014", "지원하지 않는 JWT 토큰입니다."),
    JWT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Jwt_4015", "유효하지 않은 JWT 토큰입니다.");

    ;
    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    FailType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
