package com.asyncgate.notification_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailType {

    // 예시
    // 400대  JWT_BAD_REQUEST  500 대 _JWT_BAD_REQUEST
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Jwt_4001", "잘못된 JWT Token 타입입니다."),

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
