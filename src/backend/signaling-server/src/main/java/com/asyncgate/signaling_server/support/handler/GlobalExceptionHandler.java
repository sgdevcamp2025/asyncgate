package com.asyncgate.signaling_server.support.handler;


import com.asyncgate.signaling_server.exception.SignalingServerException;
import com.asyncgate.signaling_server.support.response.FailResponse;
import jakarta.ws.rs.core.NoContentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    // UserException을 상속받은 예외를 처리하는 핸들러
    @ExceptionHandler(SignalingServerException.class)
    public FailResponse handleGlobalException(SignalingServerException exception) {
        return FailResponse.of(
                exception.getFailType().getErrorCode(),
                exception.getFailType().getMessage(),
                exception.getFailType().getStatus().value()
        );
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> handleNoContentException(NoContentException exception) {
        return ResponseEntity.noContent().build();
    }
}