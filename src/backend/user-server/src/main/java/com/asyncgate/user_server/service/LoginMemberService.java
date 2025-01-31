package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.request.LoginMemberRequestDto;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponseDto;
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
    public DefaultJsonWebTokenResponseDto execute(LoginMemberRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL));
        if (member == null) {
            throw new UserServerException(FailType.MEMBER_NOT_EXIST_EMAIL);
        }
        if (!member.getPassword().equals(requestDto.password())) {
            throw new UserServerException(FailType._INVALID_PASSWORD);
        }

        return jsonWebTokenUtil.generateDefaultJsonWebTokens(member.getId());
    }
}
