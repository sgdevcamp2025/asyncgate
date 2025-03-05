package com.asyncgate.signaling_server.infrastructure.client;

import com.asyncgate.signaling_server.domain.Member;
import com.asyncgate.signaling_server.dto.request.JoinRoomRequest;
import com.asyncgate.signaling_server.exception.FailType;
import com.asyncgate.signaling_server.exception.SignalingServerException;
import com.asyncgate.signaling_server.infrastructure.dto.response.ReadUserRoomProfileResponse;
import com.asyncgate.signaling_server.infrastructure.utility.WebClientUtil;
import com.asyncgate.signaling_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceClient {
    private final WebClientUtil webClientUtil;

    @Value("${service.member.url}")
    private String memberServiceUrl;

    public Mono<Member> fetchMemberById(String userId, String roomId, JoinRoomRequest request) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userId);

        log.info("✅ 유저가 입장 요청함 - userId: {}, roomId: {}", userId, roomId);

        return webClientUtil.get(memberServiceUrl, "/room/profile", queryParams, new ParameterizedTypeReference<SuccessResponse<ReadUserRoomProfileResponse>>() {})
                .flatMap(response -> {
                    if (response == null || response.getResult() == null) {
                        log.warn("❌ 유저 정보 조회 실패: userId={}, roomId={}", userId, roomId);
                        return Mono.error(new SignalingServerException(FailType._MEMBER_NOT_FOUND));
                    }
                    ReadUserRoomProfileResponse userProfile = response.getResult();
                    return Mono.just(Member.create(userId, roomId, userProfile.getProfileImageUrl(), userProfile.getNickname(), request.audioEnabled(), request.mediaEnabled(), request.dataEnabled()));
                })
                .doOnError(e -> log.error("❌ MemberServiceClient 오류: 유저 정보 조회 실패 (userId={}, roomId={}, message={})", userId, roomId, e.getMessage(), e));
    }
}