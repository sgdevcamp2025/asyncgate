package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeCrudRepository extends CrudRepository<AuthenticationCodeEntity, String> {
}
