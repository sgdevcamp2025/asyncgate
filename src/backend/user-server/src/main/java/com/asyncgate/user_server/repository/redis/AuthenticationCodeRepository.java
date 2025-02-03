package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import org.springframework.data.repository.CrudRepository;


public interface AuthenticationCodeRepository {
    AuthenticationCodeEntity save(AuthenticationCodeEntity authenticationCodeEntity);

    AuthenticationCodeEntity findById(String id);

    void delete(AuthenticationCodeEntity authenticationCodeEntity);
}