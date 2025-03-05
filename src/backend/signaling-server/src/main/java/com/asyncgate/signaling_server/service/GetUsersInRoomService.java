package com.asyncgate.signaling_server.service;

import com.asyncgate.signaling_server.dto.response.GetUsersInChannelResponse;
import com.asyncgate.signaling_server.signaling.KurentoManager;
import com.asyncgate.signaling_server.usecase.GetUsersInRoomUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUsersInRoomService implements GetUsersInRoomUseCase {
    private final KurentoManager kurentoManager;

    @Override
    public GetUsersInChannelResponse execute(final String roomId) {
        System.out.println("GetUsersInRoomService.execute 호출됨, roomId: " + roomId);
/*
        List<GetUsersInChannelResponse.UserInRoom> users = kurentoManager.getUsersInChannel(roomId);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOutput = objectMapper.writeValueAsString(users);
            System.out.println("🔍 현재 roomId의 users 리스트 (JSON): " + jsonOutput);
        } catch (Exception e) {
            System.out.println("🚨 JSON 변환 오류: " + e.getMessage());
        }

        return GetUsersInChannelResponse.builder()
                .channelId(roomId)
                .users(users)
                .build();

 */
        return null;
    }
}
