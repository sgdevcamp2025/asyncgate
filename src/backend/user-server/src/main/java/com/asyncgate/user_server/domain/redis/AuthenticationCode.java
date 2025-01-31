package com.asyncgate.user_server.domain.redis;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationCode {

    private final String id;
    private final String code;

    @Builder
    public AuthenticationCode(final String id, final String code) {
        this.id = id;
        this.code = code;
    }

    public static AuthenticationCode createAuthenticationCode(final String email, final String code) {
        return AuthenticationCode.builder()
                .id(email)
                .code(code)
                .build();
    }

    public boolean isValid(final String inputCode) {
        return this.code.equals(inputCode);
    }
}
