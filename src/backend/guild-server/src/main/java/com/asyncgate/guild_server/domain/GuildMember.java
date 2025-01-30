package com.asyncgate.guild_server.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildMember implements Identifiable {
    private final String id;
    private final String userId;
    private final String guildId;
    private final GuildRole guildRole;

    @Builder
    private GuildMember(String id, String userId, String guildId, GuildRole guildRole) {
        this.id = id;
        this.userId = userId;
        this.guildId = guildId;
        this.guildRole = guildRole;
    }

    public static GuildMember join(final String userId, final String guildId, final GuildRole guildRole) {
        String id = UUID.randomUUID().toString();
        return new GuildMember(id, userId, guildId, guildRole);
    }
}
