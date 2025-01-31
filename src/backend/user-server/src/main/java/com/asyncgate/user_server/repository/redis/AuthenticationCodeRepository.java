package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationCodeRepository extends CrudRepository<AuthenticationCodeEntity, String> {

    Optional<AuthenticationCodeEntity> findById(String id);

    void deleteById(String id);
}