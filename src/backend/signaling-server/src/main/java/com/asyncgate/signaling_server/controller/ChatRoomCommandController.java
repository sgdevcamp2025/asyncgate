package com.asyncgate.signaling_server.controller;

import com.asyncgate.signaling_server.dto.request.JoinRoomRequest;
import com.asyncgate.signaling_server.dto.response.GetUsersInChannelResponse;
import com.asyncgate.signaling_server.security.annotation.MemberID;
import com.asyncgate.signaling_server.usecase.GetUsersInRoomUseCase;
import com.asyncgate.signaling_server.usecase.JoinRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.asyncgate.signaling_server.support.response.SuccessResponse;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ChatRoomCommandController {

    private final JoinRoomUseCase joinRoomUseCase;
    // private final GetUsersInRoomUseCase getUsersInRoomUseCase;

    /**
     * 채널 참여
     */
    @PostMapping("/{room_id}/join")
    public SuccessResponse<String> joinRoom(@PathVariable("room_id") final String roomId, @MemberID final String memberId, @RequestBody JoinRoomRequest request) {
        joinRoomUseCase.execute(roomId, memberId, request);
        return SuccessResponse.ok("room: " + roomId + "에 참여하였습니다.");
    }

    /*
    @GetMapping("/{room_id}/users")
    public SuccessResponse<GetUsersInChannelResponse> getUsersInRoom(@PathVariable("room_id") String roomId) {
        return SuccessResponse.ok(getUsersInRoomUseCase.execute(roomId));
    }
    */

    /**
     *
     * kurento room 제거 method
     */
    @DeleteMapping("/{roomId}")
    public SuccessResponse<String> deleteRoom(@PathVariable("roomId") String roomId) {
        return SuccessResponse.ok("room: " + roomId + "가 삭제되었습니다.");
    }
}
