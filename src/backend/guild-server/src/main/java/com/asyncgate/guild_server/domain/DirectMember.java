package com.asyncgate.guild_server.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class DirectMember {
    private final String id;
    private final String directId;
    private final String memberId;
    private final String memberName;
    private LocalDateTime createdDate;

    @Builder
    private DirectMember(String id, String directId, String memberId, String memberName, LocalDateTime createdDate) {
        this.id = id;
        this.directId = directId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdDate = createdDate;
    }

    private DirectMember(String id, String directId, String memberId, String memberName) {
        this.id = id;
        this.directId = directId;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public static DirectMember create(final String directId, final String memberId, final String memberName) {
        return new DirectMember(UUID.randomUUID().toString(), directId, memberId, memberName);
    }

}
