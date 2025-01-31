package com.asyncgate.user_server.entity.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "temporary_account", timeToLive = 60 * 30) // 30분
public class TemporaryMemberEntity {

    @Id
    private String id;

    @Indexed
    private String email;

    private String password;
    private String name;
    private String nickname;
    private String deviceToken;
    private LocalDate birth;

    @Builder
    public TemporaryMemberEntity(String id, String email, String password, String name, String nickname, String deviceToken, LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.deviceToken = deviceToken;
        this.birth = birth;
    }
}