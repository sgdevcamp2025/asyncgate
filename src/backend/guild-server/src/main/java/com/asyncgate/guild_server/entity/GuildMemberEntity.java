package com.asyncgate.guild_server.entity;

import com.asyncgate.guild_server.domain.GuildInvitationStatus;
import com.asyncgate.guild_server.domain.GuildRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guild_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildMemberEntity extends BaseEntity {

    @Id
    private String id;

    private String userId;
    private String guildId;

    @Enumerated(EnumType.STRING)
    private GuildRole guildRole;

    @Enumerated(EnumType.STRING)
    private GuildInvitationStatus status = GuildInvitationStatus.PENDING;

    @Builder
    public GuildMemberEntity(String id, String userId, String guildId, GuildRole guildRole, GuildInvitationStatus status) {
        this.id = id;
        this.userId = userId;
        this.guildId = guildId;
        this.guildRole = guildRole;
        this.status = status;
    }
}
