package com.asyncgate.guild_server.domain;

import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
public class GuildMember implements Identifiable {
    private final String id;
    private final String userId;
    private final String guildId;
    private GuildRole guildRole;
    private GuildInvitationStatus status;

    @Builder
    private GuildMember(String id, String userId, String guildId, GuildRole guildRole, GuildInvitationStatus status) {
        this.id = id;
        this.userId = userId;
        this.guildId = guildId;
        this.guildRole = guildRole;
        this.status = status;
    }

    /**  길드 관리자가 직접 멤버를 추가하는 경우 */
    public static GuildMember createdByAdmin(final String userId, final String guildId) {
        String id = UUID.randomUUID().toString();
        return new GuildMember(id, userId, guildId, GuildRole.ADMIN, GuildInvitationStatus.ACCEPTED);
    }

    /**  길드 멤버가 초대된 경우 (초기 상태: PENDING) */
    public static GuildMember invited(final String userId, final String guildId) {
        String id = UUID.randomUUID().toString();
        return new GuildMember(id, userId, guildId, GuildRole.MEMBER, GuildInvitationStatus.PENDING);
    }

    /**  초대 수락 메서드 */
    public void accept() {
        if (this.status != GuildInvitationStatus.PENDING) {
            throw new GuildServerException(FailType.INVITATION_ALREADY_ACCEPTED);
        }
        this.status = GuildInvitationStatus.ACCEPTED;
    }

    /**  초대 거절 메서드 */
    public void reject() {
        if (this.status != GuildInvitationStatus.PENDING) {
            throw new GuildServerException(FailType.INVITATION_ALREADY_REJECTED);
        }
        this.status = GuildInvitationStatus.REJECTED;
    }

    /**  초대 취소 (길드 관리자가 초대를 취소하는 경우) */
    public void cancel() {
        if (this.status != GuildInvitationStatus.PENDING) {
            throw new GuildServerException(FailType.INVITATION_ALREADY_CANCELED);
        }
        this.status = GuildInvitationStatus.CANCELED;
    }

    /**  초대 만료 (시간 초과로 자동 만료되는 경우) */
    public void expire() {
        if (this.status != GuildInvitationStatus.PENDING) {
            throw new GuildServerException(FailType.INVITATION_ALREADY_EXPIRED);
        }
        this.status = GuildInvitationStatus.EXPIRED;
    }

    public boolean isAdmin() {
        return this.guildRole == GuildRole.ADMIN;
    }
}
