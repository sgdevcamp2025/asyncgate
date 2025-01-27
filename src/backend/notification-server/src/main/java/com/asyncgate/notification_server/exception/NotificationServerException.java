package com.asyncgate.notification_server.exception;

import lombok.Getter;

@Getter
public class NotificationServerException extends RuntimeException {

    private final FailType failType;

    public NotificationServerException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}