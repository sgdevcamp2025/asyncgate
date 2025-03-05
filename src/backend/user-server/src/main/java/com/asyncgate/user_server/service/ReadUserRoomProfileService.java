package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.ReadUserRoomProfileResponse;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.ReadUserRoomProfileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadUserRoomProfileService implements ReadUserRoomProfileUseCase {

    private final MemberRepository memberRepository;

    public ReadUserRoomProfileResponse execute(final String userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        // member를 ReadUserRoomProfileResponse로 변환
        return ReadUserRoomProfileResponse.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImgUrl())
                .nickname(member.getNickname())
                .build();
    }
}
