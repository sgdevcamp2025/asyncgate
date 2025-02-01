package com.asyncgate.user_server.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class TemporaryMember implements Identifiable {

    private final String id;
    private final String email;
    private String password;
    private final String name;
    private final String nickname;
    private final String deviceToken;
    private final LocalDate birth;

    @Builder
    public TemporaryMember(final String id, final String email, final String password, final String name, final String nickname, final String deviceToken, final LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.deviceToken = deviceToken;
        this.birth = birth;
    }

    public static TemporaryMember create(final String email, final String password, final String name, final String nickname, final LocalDate birth) {
        String id = UUID.randomUUID().toString();
        return new TemporaryMember(id, email, password, name, nickname, null, birth);
    }

    /**
     * 비밀번호 암호화 처리
     */
    public void encodePassword(final PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
