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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginMemberService implements LoginMemberUsecase {
    private final MemberRepository memberRepository;
    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public DefaultJsonWebTokenResponse execute(final LoginMemberRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL));

        // 비밀번호 일치 인증
        if (!bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
            throw new UserServerException(FailType._INVALID_PASSWORD);
        }

        return jsonWebTokenUtil.generateDefaultJsonWebTokens(member.getId());
    }
}
