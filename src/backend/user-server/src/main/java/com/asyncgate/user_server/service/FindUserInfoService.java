package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.UserClientInfoResponses;
import com.asyncgate.user_server.dto.response.UserClientInfoResponses.UserClientInfoResponse;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.FindUserInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindUserInfoService implements FindUserInfoUseCase {

    private final MemberRepository memberRepository;

    @Override
    public UserClientInfoResponses getByUserIds(final List<String> memberIds) {
        return UserClientInfoResponses.from(
                memberRepository.getByMemberIds(memberIds)
        );
    }

    @Override
    public UserClientInfoResponse getByUserId(final String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));
        return UserClientInfoResponse.from(member);
    }
}
