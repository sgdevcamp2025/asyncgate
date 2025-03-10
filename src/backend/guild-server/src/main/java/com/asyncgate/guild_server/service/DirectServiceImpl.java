package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.client.UserClient;
import com.asyncgate.guild_server.client.UserClientInfoResponses;
import com.asyncgate.guild_server.client.UserClientInfoResponses.UserClientInfoResponse;
import com.asyncgate.guild_server.domain.Direct;
import com.asyncgate.guild_server.domain.DirectMember;
import com.asyncgate.guild_server.dto.request.DirectChannelCreateRequest;
import com.asyncgate.guild_server.dto.response.DirectResponse;
import com.asyncgate.guild_server.dto.response.DirectResponses;
import com.asyncgate.guild_server.repository.DirectMemberRepository;
import com.asyncgate.guild_server.repository.DirectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectServiceImpl implements DirectService {

    private final UserClient userClient;
    private final DirectRepository directRepository;
    private final DirectMemberRepository directMemberRepository;

    @Override
    @Transactional
    public DirectResponse create(final String currentUserId, final DirectChannelCreateRequest request) {
        List<String> memberIds = new ArrayList<>(request.getMemberIds());
        if (!memberIds.contains(currentUserId)) {
            memberIds.add(currentUserId);
        }

        UserClientInfoResponses usersInfo = userClient
                .getUsersInfo(memberIds)
                .getResult();

        Direct direct = Direct.create();

        // ToDo memberName 변경시 이벤트 구독하고 수정해야함
        List<DirectMember> directMembers = usersInfo.responses().stream()
                .map(userInfo ->
                        DirectMember.create(direct.getId(), userInfo.userId(), userInfo.name())
                )
                .toList();

        directRepository.save(direct);
        directMemberRepository.saveAll(directMembers);

        return DirectResponse.of(direct.getId(), usersInfo);
    }

    @Override
    public DirectResponses getDirectList(final String currentUserId) {
        // 1. 현재 사용자의 다이렉트 메시지 목록 조회
        List<DirectMember> directMembers = directMemberRepository.getDirectMessageList(currentUserId);

        // 2. 다이렉트 ID별로 DirectMember 그룹화
        Map<String, List<DirectMember>> directGroups = directMembers.stream()
                .collect(Collectors.groupingBy(DirectMember::getDirectId));

        // 3. 모든 그룹에서 고유한 사용자 ID 수집 (한 번의 배치 API 호출을 위함)
        Set<String> uniqueMemberIds = directMembers.stream()
                .map(DirectMember::getMemberId)
                .collect(Collectors.toSet());

        // 4. 배치 API 호출로 모든 사용자 프로필 정보 조회
        UserClientInfoResponses userInfos = userClient
                .getUsersInfo(new ArrayList<>(uniqueMemberIds))
                .getResult();

        // 5. 사용자 ID별 프로필 정보를 빠르게 조회할 수 있도록 Map으로 변환
        Map<String, UserClientInfoResponse> userInfoMap = userInfos.responses().stream()
                .collect(
                        Collectors.toMap(
                                UserClientInfoResponse::userId,
                                Function.identity()
                        )
                );

        // 6. 각 다이렉트 그룹별로 응답 객체를 생성
        List<DirectResponse> responses = directGroups.entrySet().stream()
                .map(entry -> {
                    String directId = entry.getKey();
                    List<DirectMember> membersInGroup = entry.getValue();

                    // 해당 그룹에 속한 멤버들의 프로필 정보를 매핑 (null은 필터링)
                    List<UserClientInfoResponse> memberProfiles = membersInGroup.stream()
                            .map(directMember -> userInfoMap.get(directMember.getMemberId()))
                            .filter(Objects::nonNull)
                            .toList();

                    return DirectResponse.of(directId, new UserClientInfoResponses(memberProfiles));
                }).toList();
        return DirectResponses.from(responses);
    }

    @Override
    public List<String> getDirectDetail(final String directId) {
        return directMemberRepository.getDirectMemberIds(directId);
    }

}
