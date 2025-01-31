package com.asyncgate.user_server.dto.request;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record RegisterTemporaryMemberRequestDto(

        @JsonProperty("email")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "잘못된 이메일 형식입니다."
        )
        String email,

        @JsonProperty("password")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        String password,

        @JsonProperty("name")
        String name,

        @JsonProperty("nickname")
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @JsonProperty("birth")
        @NotBlank(message = "생년월일을 입력해주세요.")
        LocalDate birth
) {
}
