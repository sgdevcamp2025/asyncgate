package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.DeleteUserUseCase;
import org.springframework.transaction.annotation.Transactional;

public class DeleteUserService implements DeleteUserUseCase {

    MemberRepository memberRepository;

    @Override
    @Transactional
    public void execute(String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }
}
