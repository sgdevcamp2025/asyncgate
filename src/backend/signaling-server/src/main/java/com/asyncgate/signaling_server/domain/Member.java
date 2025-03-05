package com.asyncgate.signaling_server.domain;

import com.asyncgate.signaling_server.entity.type.MemberMediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member implements Identifiable {

    private final String id; // 고유한 식별자
    private final String progileImageUrl;
    private final String nickname;
    private String roomId; // 사용자가 속한 방 ID

    // 미디어 상태
    private boolean isAudioEnabled;
    private boolean isMediaEnabled;
    private boolean isDataEnabled;

    @Builder
    public Member(final String id, final String roomId, final String progileImageUrl, final String nickname, final boolean isAudioEnabled, final boolean isMediaEnabled, final boolean isDataEnabled) {
        this.id = id;
        this.roomId = roomId;
        this.progileImageUrl = progileImageUrl;
        this.nickname = nickname;
        this.isAudioEnabled = isAudioEnabled;
        this.isMediaEnabled = isMediaEnabled;
        this.isDataEnabled = isDataEnabled;
    }

    public static Member create(final String id, final String roomId, final String progileImageUrl, final String nickname, final boolean isAudioEnabled, final boolean isMediaEnabled, final boolean isDataEnabled) {
        return Member.builder()
                .id(id)
                .roomId(roomId)
                .progileImageUrl(progileImageUrl)
                .nickname(nickname)
                .isAudioEnabled(isAudioEnabled)
                .isMediaEnabled(isMediaEnabled)
                .isDataEnabled(isDataEnabled)
                .build();
    }

    // 미디어 상태 업데이트
    public void updateMediaState(MemberMediaType type, boolean enabled) {
        switch (type) {  // ✅ Enum 자체를 switch에 사용
            case AUDIO:
                this.isAudioEnabled = enabled;
                break;
            case MEDIA:
                this.isMediaEnabled = enabled;
                break;
            case DATA:
                this.isDataEnabled = enabled;
                break;
            default:
                throw new IllegalArgumentException("잘못된 미디어 타입: " + type);
        }
    }
}