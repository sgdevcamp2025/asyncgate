package com.asyncgate.user_server.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Member implements Identifiable {
    private final String id;
    private final String email;
    private String password;
    private String deviceToken;
    private String name;
    private String nickname;
    private String profileImgUrl;
    private LocalDate birth;

    @Builder
    private Member(final String id, final String email, final String password, final String name, final String nickname, final String profileImgUrl, final String deviceToken, final LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.deviceToken = deviceToken;
        this.birth = birth;
    }

    public static Member create(final String email, final String password, final String name, final String nickname, final String deviceToken, final String profileImgUrl, final LocalDate birth) {
        String id = UUID.randomUUID().toString();
        return new Member(id, email, password, name, nickname, profileImgUrl, deviceToken, birth);
    }

    public void update(String name, String nickname, String profileImageUrl) {
            this.name = name;
            this.nickname = nickname;
            this.profileImgUrl = profileImageUrl;

    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    // device token이 NULL인 겨우
    public String getDeviceToken() {
        return (deviceToken != null) ? deviceToken : "NO_DEVICE";
    }
}
