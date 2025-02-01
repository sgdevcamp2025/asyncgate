package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryMemberRepository extends CrudRepository<TemporaryMemberEntity, String> {
    Optional<TemporaryMemberEntity> findByEmail(String email);

    void deleteById(String id);
}