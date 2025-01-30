package com.asyncgate.guild_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildResponse {

    private String guildId;
    private String name;
    private boolean isPrivate;

    public static GuildResponse of(final String guildId, final String name, final boolean isPrivate) {
        return new GuildResponse(guildId, name, isPrivate);
    }
}
