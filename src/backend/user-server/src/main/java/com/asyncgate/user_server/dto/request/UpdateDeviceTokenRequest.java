package com.asyncgate.user_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateDeviceTokenRequest(

        @Schema(description = "디바이스 토큰", example = "fcm_token_value")
        @JsonProperty("device_token")
        String deviceToken
) {
}
