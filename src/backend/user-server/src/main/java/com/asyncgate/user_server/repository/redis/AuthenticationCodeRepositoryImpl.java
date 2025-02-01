package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthenticationCodeRepositoryImpl implements AuthenticationCodeRepository {
    private final AuthenticationCodeCrudRepository authenticationCodeCrudRepository;

    @Override
    public AuthenticationCodeEntity save(AuthenticationCodeEntity authenticationCodeEntity) {
        return authenticationCodeCrudRepository.save(authenticationCodeEntity);
    }

    @Override
    public void delete(AuthenticationCodeEntity authenticationCodeEntity) {
        authenticationCodeCrudRepository.delete(authenticationCodeEntity);
    }

    @Override
    public AuthenticationCodeEntity findById(String id) {
        return authenticationCodeCrudRepository.findById(id)
                .orElseThrow(() -> new UserServerException(FailType.EMAIL_AUTH_CODE_NOT_FOUND));
    }
}
