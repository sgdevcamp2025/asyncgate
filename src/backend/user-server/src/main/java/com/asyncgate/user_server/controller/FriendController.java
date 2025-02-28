package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.controller.docs.FriendControllerDocs;
import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.FriendResponse;
import com.asyncgate.user_server.dto.response.FriendsResponse;
import com.asyncgate.user_server.dto.response.MemberResponse;
import com.asyncgate.user_server.security.annotation.MemberID;
import com.asyncgate.user_server.support.response.SuccessResponse;
import com.asyncgate.user_server.usecase.FriendUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController implements FriendControllerDocs {

    private final FriendUseCase friendUseCase;

    /**
     * 회원 검색 (이메일 기준)
     */
    @Override
    @GetMapping
    public SuccessResponse<MemberResponse> searchTarget(final @RequestParam String email) {
        Member findMember = friendUseCase.getByEmail(email);
        return SuccessResponse.ok(
                MemberResponse.from(findMember)
        );
    }

    /**
     * 친구 요청: 현재 사용자(userId)가 toUserId에게 친구 요청
     * URL 예시: POST /friends/request/{toUserId}
     */
    @Override
    @PostMapping("/request/{toUserId}")
    public SuccessResponse<FriendResponse> requestFriend(
            final @MemberID String userId,
            final @PathVariable String toUserId
    ) {
        Friend friend = friendUseCase.registerFriend(userId, toUserId);
        return SuccessResponse.created(
                FriendResponse.from(friend)
        );
    }

    /**
     * 친구 수락: 현재 사용자(userId)가 friendId에 해당하는 친구 요청을 수락
     * URL 예시: POST /friends/accept/{friendId}
     */
    @Override
    @PostMapping("/accept/{friendId}")
    public SuccessResponse<FriendResponse> acceptFriend(
            final @MemberID String userId,
            final @PathVariable String friendId
    ) {
        Friend friend = friendUseCase.acceptFriend(userId, friendId);
        return SuccessResponse.ok(
                FriendResponse.from(friend)
        );
    }

    /**
     * 친구 거절: 현재 사용자(userId)가 friendId에 해당하는 친구 요청을 거절
     * URL 예시: POST /friends/reject/{friendId}
     */
    @Override
    @PostMapping("/reject/{friendId}")
    public SuccessResponse<FriendResponse> rejectFriend(
            final @MemberID String userId,
            @PathVariable String friendId) {
        Friend friend = friendUseCase.rejectFriend(userId, friendId);
        return SuccessResponse.ok(FriendResponse.from(friend));
    }

    /**
     * 친구 삭제(soft delete): 현재 사용자(userId)가 friendId에 해당하는 친구 관계를 soft delete 처리
     * URL 예시: DELETE /friends/{friendId}
     */
    @Override
    @DeleteMapping("/{friendId}")
    public SuccessResponse<String> deleteFriend(
            final @MemberID String userId,
            final @PathVariable String friendId
    ) {
        friendUseCase.deleteFriend(userId, friendId);
        return SuccessResponse.ok(String.format("UserId[&s]인 친구를 삭제했습니다.", friendId));
    }

    /**
     * 본인이 보낸 친구 요청 목록 조회 (상태: PENDING)
     * URL 예시: GET /friends/sent
     */
    @Override
    @GetMapping("/sent")
    public SuccessResponse<FriendsResponse> getSentFriendRequests(final @MemberID String userId) {
        return SuccessResponse.ok(
                friendUseCase.getSentFriendRequests(userId)
        );
    }

    /**
     * 본인이 받은 친구 요청 목록 조회 (상태: PENDING)
     * URL 예시: GET /friends/received
     */
    @Override
    @GetMapping("/received")
    public SuccessResponse<FriendsResponse> getReceivedFriendRequests(final @MemberID String userId) {
        return SuccessResponse.ok(
                friendUseCase.getReceivedFriendRequests(userId)
        );
    }

    /**
     * 본인의 실제 친구 목록 조회 (상태: ACCEPTED)
     * URL 예시: GET /friends/list
     */
    @Override
    @GetMapping("/list")
    public SuccessResponse<FriendsResponse> getFriends(final @MemberID String userId) {
        return SuccessResponse.ok(
                friendUseCase.getFriends(userId)
        );
    }
}
