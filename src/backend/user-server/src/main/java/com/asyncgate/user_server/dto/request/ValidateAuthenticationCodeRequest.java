package com.asyncgate.user_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidateAuthenticationCodeRequest(

        @Schema(description = "이메일", example = "email@gmail.com")
        @JsonProperty("email")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "잘못된 이메일 형식입니다.")
        String email,

        @Schema(description = "인증 코드", example = "123456")
        @JsonProperty("authentication_code")
        @NotBlank(message = "인증 코드를 입력해주세요.")
        @Pattern(regexp = "^[0-9]{6}$", message = "인증 코드는 6자리의 숫자로 이루어져 있습니다.")
        String authenticationCode
) {
}
