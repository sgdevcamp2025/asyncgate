package com.asyncgate.apigatewayserver.exception;

import lombok.Getter;

@Getter
public class ApiGatewayServerException extends RuntimeException {

    private final FailType failType;

    public ApiGatewayServerException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}