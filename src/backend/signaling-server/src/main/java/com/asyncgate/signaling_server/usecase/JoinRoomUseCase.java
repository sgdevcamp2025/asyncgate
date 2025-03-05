package com.asyncgate.signaling_server.usecase;

import com.asyncgate.signaling_server.dto.request.JoinRoomRequest;

public interface JoinRoomUseCase {
    /**
     * 채팅방 참여
     *
     * @param roomId
     * @param memberId
     * @param request
     */
    void execute(final String roomId, final String memberId, final JoinRoomRequest request);
}
