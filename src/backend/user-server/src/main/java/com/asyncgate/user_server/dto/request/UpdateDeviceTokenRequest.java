package com.asyncgate.user_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateDeviceTokenRequest(

        @JsonProperty("device_token")
        String deviceToken
) {
}
