package com.asyncgate.signaling_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUsersInChannelResponse {
    // 유저 닉네임, 유저 아이디, 프로필 사진, 마이크 활성화 여부, 카메라 활성화 여부, 화면 공유 활성화 여부
    @JsonProperty("channel_id")
    private final String channelId;

    @JsonProperty("users")
    private final List<UserInRoom> users;

    @Builder
    public GetUsersInChannelResponse(String channelId, List<UserInRoom> users) {
        this.channelId = channelId;
        this.users = users;
    }

    @Getter
    public static class UserInRoom {
        @JsonProperty("id")
        private final String id;

        @JsonProperty("nickname")
        private final String nickname;

        @JsonProperty("profile_image")
        private final String profileImage;

        @JsonProperty("is_mic_enabled")
        private final boolean isMicEnabled;

        @JsonProperty("is_camera_enabled")
        private final boolean isCameraEnabled;

        @JsonProperty("is_screen_sharing_enabled")
        private final boolean isScreenSharingEnabled;

        @Builder
        public UserInRoom(final String id, final String nickname, final String profileImage, final boolean isMicEnabled, final boolean isCameraEnabled, final boolean isScreenSharingEnabled) {
            this.id = id;
            this.nickname = nickname;
            this.profileImage = profileImage;
            this.isMicEnabled = isMicEnabled;
            this.isCameraEnabled = isCameraEnabled;
            this.isScreenSharingEnabled = isScreenSharingEnabled;
        }
    }
}
