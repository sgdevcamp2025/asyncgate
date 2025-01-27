package com.asyncgate.signaling_server.exception;

import lombok.Getter;

@Getter
public class SignalingServerException extends RuntimeException {

    private final FailType failType;

    public SignalingServerException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}