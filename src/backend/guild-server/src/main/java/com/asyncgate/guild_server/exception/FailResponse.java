package com.asyncgate.guild_server.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@JsonPropertyOrder({"httpStatus", "errorCode", "message", "time"})
public class FailResponse {

    @JsonProperty("httpStatus")
    private final int httpStatus;

    private final String errorCode;
    private final String message;
    private final LocalDateTime time;

    private FailResponse(final String errorCode, final String message, final int httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.time = LocalDateTime.now();
        this.httpStatus = httpStatus;
    }

    public static FailResponse of(
            final String errorCode,
            final String message,
            final int httpStatus
    ) {
        return new FailResponse(errorCode, message, httpStatus);
    }
}
