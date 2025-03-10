package com.asyncgate.user_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DefaultJsonWebTokenResponse {

    @Schema(description = "유저 ID", example = "idididid")
    @JsonProperty("user_id")
    @NotBlank
    private final String userId;

    @Schema(description = "유저 Nickname", example = "idididid")
    @JsonProperty("nickname")
    @NotBlank
    private final String nickname;

    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    @JsonProperty("access_token")
    @NotBlank
    private final String accessToken;

    @Builder
    public DefaultJsonWebTokenResponse(
            String userId,
            String nickname,
            String accessToken
    ) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = accessToken;
    }
}
