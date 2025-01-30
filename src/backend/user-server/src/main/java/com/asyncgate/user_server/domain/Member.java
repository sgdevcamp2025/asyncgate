package com.asyncgate.user_server.domain;

import com.asyncgate.user_server.entity.MemberEntity;
import lombok.Getter;
import java.util.UUID;

@Getter
public class Member {

    private final UUID id;
    private final String email;
    private String password;
    private String deviceToken;
    private String nickname;
    private String profileImgUrl;

    public Member(UUID id, String email, String password, String nickname, String profileImgUrl, String deviceToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateProfile(String nickname, String profileImgUrl) {
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public MemberEntity toEntity() {
        return new MemberEntity(email, password, nickname, profileImgUrl);
    }

    public static Member fromEntity(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getProfileImgUrl(),
                entity.getDeviceToken()
        );
    }
}
