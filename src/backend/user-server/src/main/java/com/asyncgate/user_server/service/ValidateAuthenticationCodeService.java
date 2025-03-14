package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {
    private final MemberRepository memberRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    @Value("${cloud.aws.s3.profile.default.url}")
    private String defaultProfileImageUrl;

    @Override
    @Transactional
    public void execute(final ValidateAuthenticationCodeRequest request) {

        AuthenticationCodeEntity storedAuthCode = authenticationCodeRepository.findById(request.email());

        String redisSaveCode = storedAuthCode.getCode();
        String requestCode = request.authenticationCode();
        if (!redisSaveCode.equals(requestCode)) {
            throw new UserServerException(FailType.INVALID_EMAIL_AUTH_CODE);
        }

        // 임시 회원 정보 get
        TemporaryMemberEntity tempMember = temporaryMemberRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserServerException(FailType.TEMPORARY_MEMBER_NOT_FOUND));

        Member member = Member.create(tempMember.getEmail(), tempMember.getPassword(), tempMember.getName(),
                tempMember.getNickname(), tempMember.getDeviceToken(), defaultProfileImageUrl, tempMember.getBirth());

        memberRepository.save(member);

        authenticationCodeRepository.delete(storedAuthCode);

        temporaryMemberRepository.delete(tempMember);

    }
}