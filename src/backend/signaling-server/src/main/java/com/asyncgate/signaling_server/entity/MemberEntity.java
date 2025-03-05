package com.asyncgate.signaling_server.entity;

import com.asyncgate.signaling_server.entity.type.MemberMediaType;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    @Id
    private String id;

    private String roomId;  // 참가 중인 방 ID

    private String profileImageUrl;
    private String nickname;

    private boolean isAudioEnabled; // 오디오
    private boolean isMediaEnabled; // 화상 공유
    private boolean isDataEnabled; // 화면 공유
}
