package com.asyncgate.user_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class CheckEmailDuplicateResponse {

    @Schema(description = "이메일 중복 여부", example = "true : 존재하는 이메일 | false : 존재하지 않는 이메일")
    @JsonProperty("is_duplicate")
    @NotBlank
    private final boolean isDuplicate;

    @Builder
    public CheckEmailDuplicateResponse(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }
}
