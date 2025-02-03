package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void execute(final String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        memberRepository.softDeleteById(member.getId());
    }
}
