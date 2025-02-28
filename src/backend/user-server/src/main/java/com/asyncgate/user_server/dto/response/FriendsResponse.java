package com.asyncgate.user_server.dto.response;

import com.asyncgate.user_server.domain.FriendStatus;
import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.UserClientInfoResponses.UserClientInfoResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 친구 목록 응답 DTO
 * <p>
 * 친구 목록은 다음과 같은 상태에 따라 다를 수 있습니다:
 * - `PENDING`: 사용자가 요청한 친구 목록
 * - `RECEIVED`: 사용자가 요청을 받은 친구 목록
 * - `ACCEPTED`: 실제 친구 목록 (요청을 수락한 친구 목록)
 */
@Schema(description = "상태에 맞는 친구 목록 응답")
public record FriendsResponse(

        @ArraySchema(schema = @Schema(description = "상태에 맞는 친구 목록", implementation = UserClientInfoResponse.class))
        List<UserClientInfoResponse> friends,

        @Schema(
                description = "친구 목록들과의 관계 상태 (예: PENDING, ACCEPTED, REJECTED)",
                example = "ACCEPTED"
        )
        FriendStatus status
) {
    public static FriendsResponse of(final List<Member> members, final FriendStatus status) {
        return new FriendsResponse(
                members.stream()
                        .map(UserClientInfoResponse::from)
                        .toList(),
                status
        );
    }
}
