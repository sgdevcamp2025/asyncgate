package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.entity.MemberEntity;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.support.utility.DomainUtil;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(final Member member) {
        MemberEntity memberEntity = DomainUtil.MemberMapper.toEntity(member);

        memberJpaRepository.save(memberEntity);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email)
                .map(DomainUtil.MemberMapper::toDomain);
    }

    @Override
    public Optional<Member> findById(final String id) {
        return memberJpaRepository.findById(id)
                .map(DomainUtil.MemberMapper::toDomain);
    }

    @Override
    public boolean isExistByEmail(final String email) {
        return memberJpaRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isExistById(final String id) {
        return memberJpaRepository.findById(id).isPresent();
    }

    private MemberEntity findMemberEntityByEmail(final String email) {
        return memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL));
    }

    private MemberEntity findMemberEntityById(final String id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));
    }
}
