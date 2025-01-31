package com.asyncgate.user_server.domain.redis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
public class TemporaryMember {

    private final String id;
    private final String email;
    private String password;
    private String name;
    private String nickname;
    private String deviceToken;
    private LocalDate birth;

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

    public static TemporaryMember createTemporaryMember(final String id, final String email, final String password, final String name, final String nickname, final String deviceToken, final LocalDate birth) {
        return TemporaryMember.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .deviceToken(deviceToken)
                .birth(birth)
                .build();
    }

    /**
     * 비밀번호 암호화 처리
     */
    public void encodePassword(final PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
