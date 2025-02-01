package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;

import java.util.Optional;

public interface TemporaryMemberRepository {
    Optional<TemporaryMemberEntity> findByEmail(String email);

    void delete(TemporaryMemberEntity temporaryMemberEntity);

    void save(TemporaryMemberEntity temporaryMemberEntity);
}