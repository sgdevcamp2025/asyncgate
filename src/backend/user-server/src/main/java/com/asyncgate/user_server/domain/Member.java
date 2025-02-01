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

    public static Member create(final String email, final String password, final String name, final String nickname, final String deviceToken, final LocalDate birth) {
        String id = UUID.randomUUID().toString();
        return new Member(id, email, password, name, nickname, null, deviceToken, birth);
    }


    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    // device token이 NULL인 겨우
    public String getDeviceToken() {
        return (deviceToken != null) ? deviceToken : "NO_DEVICE";
    }
}
