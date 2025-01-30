package com.asyncgate.user_server.domain;

import com.asyncgate.user_server.entity.MemberEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Member {

    private final UUID id;
    private final String email;
    private String password;
    private String deviceToken;
    private String name;
    private String nickname;
    private String profileImgUrl;
    private LocalDate birth;

    public Member(UUID id, String email, String password, String name, String nickname, String profileImgUrl, String deviceToken, LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.deviceToken = deviceToken;
        this.birth = birth;
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

    public MemberEntity toEntity() {
        return new MemberEntity(email, password, name, nickname, profileImgUrl, birth);
    }

    public static Member fromEntity(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getNickname(),
                entity.getProfileImgUrl(),
                entity.getDeviceToken(),
                entity.getBirth()
        );
    }

    // device token이 NULL인 겨우
    public String getDeviceToken() {
        return (deviceToken != null) ? deviceToken : "NO_DEVICE";
    }
}
