package com.asyncgate.user_server.security.exception;

import com.asyncgate.user_server.exception.FailType;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class SecurityException extends JwtException {
    private final FailType failType;

    public SecurityException(String message, FailType failType) {
        super(message);

        this.failType = failType;
    }
}
