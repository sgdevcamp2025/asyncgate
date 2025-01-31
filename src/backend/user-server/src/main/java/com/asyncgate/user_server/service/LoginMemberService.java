package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.request.LoginMemberRequest;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.security.utility.JsonWebTokenUtil;
import com.asyncgate.user_server.usecase.LoginMemberUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginMemberService implements LoginMemberUsecase {
    private final MemberRepository memberRepository;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public DefaultJsonWebTokenResponse execute(final LoginMemberRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL));
        if (member == null) {
            throw new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL);
        }
        if (!member.getPassword().equals(request.password())) {
            throw new UserServerException(FailType._INVALID_PASSWORD);
        }

        return jsonWebTokenUtil.generateDefaultJsonWebTokens(member.getId());
    }
}
