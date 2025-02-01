package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.request.UpdateDeviceTokenRequest;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.UpdateDeviceTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {
    private final MemberRepository memberRepository;

    @Override
    public void execute(final String userId, final UpdateDeviceTokenRequest request) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        member.updateDeviceToken(request.deviceToken());

        memberRepository.save(member);
    }
}
