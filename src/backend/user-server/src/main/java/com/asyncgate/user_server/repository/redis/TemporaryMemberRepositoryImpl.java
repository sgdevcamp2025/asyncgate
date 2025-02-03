package com.asyncgate.user_server.repository.redis;

import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TemporaryMemberRepositoryImpl implements TemporaryMemberRepository {
    private final TemporaryMemberCrudRepository temporaryMemberCrudRepository;

    @Override
    public void save(TemporaryMemberEntity temporaryMemberEntity) {
        temporaryMemberCrudRepository.save(temporaryMemberEntity);
    }

    @Override
    public Optional<TemporaryMemberEntity> findByEmail(String email) {
        return temporaryMemberCrudRepository.findByEmail(email);
    }


    @Override
    public void delete(TemporaryMemberEntity temporaryMemberEntity) {
        temporaryMemberCrudRepository.delete(temporaryMemberEntity);
    }
}
