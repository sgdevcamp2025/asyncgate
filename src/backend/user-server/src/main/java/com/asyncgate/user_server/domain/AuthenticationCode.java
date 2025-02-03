package com.asyncgate.user_server.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationCode implements Identifiable {

    private final String id;
    private final String code;

    @Builder
    private AuthenticationCode(final String id, final String code) {
        this.id = id;
        this.code = code;
    }

    public static AuthenticationCode create(final String email, final String code) {
        return new AuthenticationCode(email, code);
    }

    public boolean isValid(final String inputCode) {
        return this.code.equals(inputCode);
    }
}
