package com.asyncgate.apigatewayserver.exception;

import com.asyncgate.apigatewayserver.support.response.FailType;
import lombok.Getter;

@Getter
public class JwtValidationException extends RuntimeException {

    private final FailType failType;

    public JwtValidationException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}