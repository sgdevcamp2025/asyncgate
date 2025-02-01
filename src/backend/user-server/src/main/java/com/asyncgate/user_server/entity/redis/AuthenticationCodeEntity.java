package com.asyncgate.user_server.entity.redis;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "auth_code", timeToLive = 60 * 5) // 5분 동안 유지
public class AuthenticationCodeEntity {

    @Id
    private String id;

    private String code;

    @Builder
    private AuthenticationCodeEntity(String id, String code) {
        this.id = id;
        this.code = code;
    }
}