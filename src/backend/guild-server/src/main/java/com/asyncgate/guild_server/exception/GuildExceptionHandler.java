package com.asyncgate.guild_server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GuildExceptionHandler {

    @ExceptionHandler({GuildServerException.class})
    public ResponseEntity<FailResponse> handleApiException(final GuildServerException e) {
        FailType errorType = e.getFailType();
        FailResponse response = FailResponse.of(
                errorType.getErrorCode(),
                errorType.getMessage(),
                errorType.getStatus().value()
        );
        return ResponseEntity.status(errorType.getStatus()).body(response);
    }
}
