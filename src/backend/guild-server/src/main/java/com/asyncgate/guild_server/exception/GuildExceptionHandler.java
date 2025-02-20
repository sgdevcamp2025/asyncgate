package com.asyncgate.guild_server.exception;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
            return "유효성 검사에 실패했습니다.";
        } else {
            return FailType.ARGUMENT_BAD_REQUEST.getMessage();
        }
    }
}
