package com.asyncgate.user_server.security.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.security.info.CustomUserPrincipal;
import com.asyncgate.user_server.security.usecase.AuthenticateJsonWebTokenUseCase;
import com.asyncgate.user_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {
    private final MemberRepository memberRepository;

    @Override
    public CustomUserPrincipal execute(final String id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        return CustomUserPrincipal.create(DomainUtil.MemberMapper.toEntity(member));
    }
}
