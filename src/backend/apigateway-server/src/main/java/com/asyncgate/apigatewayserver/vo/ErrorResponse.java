package com.asyncgate.apigatewayserver.vo;

public record ErrorResponse(int status, String error, String message) {

    public static ErrorResponse of(final int status, final String error, final String message) {
        return new ErrorResponse(status, error, message);
    }
}