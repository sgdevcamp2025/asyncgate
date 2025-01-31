package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequestDto;
import com.asyncgate.user_server.entity.MemberEntity;
import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.repository.redis.AuthenticationCodeRepository;
import com.asyncgate.user_server.repository.redis.TemporaryMemberRepository;
import com.asyncgate.user_server.usecase.ValidateAuthenticationCodeUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {
    private final MemberRepository memberRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    @Override
    @Transactional
    public void execute(ValidateAuthenticationCodeRequestDto requestDto) {

        AuthenticationCodeEntity storedAuthCode = authenticationCodeRepository.findById(requestDto.email())
                .orElseThrow(() -> new UserServerException(FailType._EMAIL_AUTH_CODE_NOT_FOUND));

        if (!storedAuthCode.getCode().equals(requestDto.authenticationCode())) {
            throw new UserServerException(FailType._INVALID_EMAIL_AUTH_CODE);
        }

        // 임시 회원 정보 get
        TemporaryMemberEntity tempMember = temporaryMemberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new UserServerException(FailType.TEMPORARY_MEMBER_NOT_FOUND));

        log.info("tempMember: {}", tempMember);

        Member member = Member.createMember(tempMember.getEmail(), tempMember.getPassword(), tempMember.getName(),
                tempMember.getNickname(), tempMember.getDeviceToken(), tempMember.getBirth());

        memberRepository.save(member);

        authenticationCodeRepository.delete(storedAuthCode);

        temporaryMemberRepository.delete(tempMember);

    }
}