package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporaryMemberCrudRepository extends CrudRepository<TemporaryMemberEntity, String> {
    Optional<TemporaryMemberEntity> findByEmail(String email);
}
