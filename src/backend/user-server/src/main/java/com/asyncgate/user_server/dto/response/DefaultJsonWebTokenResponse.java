package com.asyncgate.user_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DefaultJsonWebTokenResponse {

    @JsonProperty("access_token")
    @NotBlank
    private final String accessToken;

    @Builder
    public DefaultJsonWebTokenResponse(
            String accessToken
    ) {
        this.accessToken = accessToken;
    }
}
