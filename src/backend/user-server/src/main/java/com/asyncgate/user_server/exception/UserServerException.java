package com.asyncgate.user_server.exception;

import lombok.Getter;

@Getter
public class UserServerException extends RuntimeException {

    private final FailType failType;

    public UserServerException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}