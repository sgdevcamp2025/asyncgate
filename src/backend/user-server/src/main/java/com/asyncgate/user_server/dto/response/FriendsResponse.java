package com.asyncgate.user_server.dto.response;

import com.asyncgate.user_server.domain.FriendStatus;
import com.asyncgate.user_server.domain.Member;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Schema(description = "상태에 맞는 친구 목록 응답")
public record FriendsResponse(
        @ArraySchema(schema = @Schema(description = "상태에 맞는 친구 목록", implementation = FriendInformation.class))
        List<FriendInformation> friends,

        @Schema(
                description = "친구 목록들과의 관계 상태 (예: PENDING, ACCEPTED, REJECTED)",
                example = "ACCEPTED"
        )
        FriendStatus status
) {
    public static FriendsResponse of(final List<FriendQueryDto> friendQueryDtos, final List<Member> members, final FriendStatus status) {
        Map<String, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));
        List<FriendInformation> friendInfos = friendQueryDtos.stream()
                .map(dto -> {
                    Member member = memberMap.get(dto.friendId());
                    return new FriendInformation(
                            dto.friendId(),
                            member.getId(),
                            member.getName(),
                            member.getNickname(),
                            member.getProfileImgUrl(),
                            member.getEmail(),
                            member.getBirth()
                    );
                })
                .toList();
        return new FriendsResponse(friendInfos, status);
    }

    public record FriendInformation(
            String friendId,
            String userId,
            String name,
            String nickname,
            String profileImageUrl,
            String email,
            LocalDate birth
    ) { }
}
