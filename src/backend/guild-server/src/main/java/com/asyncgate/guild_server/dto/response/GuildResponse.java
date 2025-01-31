package com.asyncgate.guild_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildResponse {

    private String guildId;
    private String name;
    private boolean isPrivate;
    private String profileImageUrl;

    public static GuildResponse of(
            final String guildId, final String name,
            final boolean isPrivate, final String profileImageUrl
    ) {
        return new GuildResponse(guildId, name, isPrivate, profileImageUrl);
    }
}
