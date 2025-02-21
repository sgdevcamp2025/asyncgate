package com.asyncgate.user_server.support.handler;

import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.support.response.FailResponse;
import jakarta.ws.rs.core.NoContentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    // UserException을 상속받은 예외를 처리하는 핸들러
    @ExceptionHandler(UserServerException.class)
    public FailResponse handleGlobalException(UserServerException exception) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FailType argumentBadRequest = FailType.ARGUMENT_BAD_REQUEST;
        String errorMessage = getErrorMessage(ex);

        FailResponse response = FailResponse.of(
                argumentBadRequest.getErrorCode(),
                errorMessage,
                argumentBadRequest.getStatus().value()
        );

        return ResponseEntity.status(argumentBadRequest.getStatus()).body(response);
    }

    private String getErrorMessage(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        if (StringUtils.isBlank(errorMessage)) {
            return FailType.ARGUMENT_BAD_REQUEST.getMessage();
        } else {
            return errorMessage;
        }
    }
}