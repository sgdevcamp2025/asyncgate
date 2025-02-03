package com.asyncgate.user_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class CheckEmailDuplicateResponse {

    @JsonProperty("is_duplicate")
    @NotBlank
    private final boolean isDuplicate;

    @Builder
    public CheckEmailDuplicateResponse(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }
}
