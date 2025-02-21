package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.AuthenticationCode;
import com.asyncgate.user_server.domain.TemporaryMember;
import com.asyncgate.user_server.dto.request.RegisterTemporaryMemberRequest;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.repository.redis.AuthenticationCodeRepository;
import com.asyncgate.user_server.repository.redis.TemporaryMemberRepository;
import com.asyncgate.user_server.support.utility.DomainUtil;
import com.asyncgate.user_server.support.utility.EmailUtil;
import com.asyncgate.user_server.support.utility.PasswordUtil;
import com.asyncgate.user_server.usecase.RegisterTemporaryMemberUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterTemporaryMemberService implements RegisterTemporaryMemberUseCase {

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemporaryMemberRepository temporaryMemberRepository;
    private final EmailUtil emailUtil;

    @Override
    @Transactional
    public void execute(final RegisterTemporaryMemberRequest request) {
        if (isDuplicatedEmail(request.email())) {
            throw new UserServerException(FailType.ALREADY_EXIST_EMAIL);
        }

        // 도메인 객체 생성
        TemporaryMember tempMember = TemporaryMember.builder()
                .id(UUID.randomUUID().toString()) // 고유 ID 생성
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .nickname(request.nickname())
                .birth(request.birth())
                .build();

        tempMember.encodePassword(passwordEncoder);

        // 임시 계정 생성
        temporaryMemberRepository.save(DomainUtil.TemporaryMemberMapper.toEntity(tempMember));

        String code = PasswordUtil.generateAuthCode(6);
        System.out.println("code = " + code);

        AuthenticationCode authCode = AuthenticationCode.create(request.email(), code);

        authenticationCodeRepository.save(DomainUtil.AuthenticationCodeMapper.toEntity(authCode));

        // 메일 전송 (추후 비동기로 변경)
        emailUtil.sendAuthenticationCode(request.email(), code);
    }

    private Boolean isDuplicatedEmail(
            final String email
    ) {
        return memberRepository.isExistByEmail(email);
    }

}
