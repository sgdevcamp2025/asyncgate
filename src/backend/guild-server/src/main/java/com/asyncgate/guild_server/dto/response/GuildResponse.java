package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildResponse {

    private String guildId;
    private String name;
    private boolean isPrivate;
    private String profileImageUrl;

    public static GuildResponse from(final Guild guild) {
        return new GuildResponse(guild.getId(), guild.getName(), guild.isPrivate(), guild.getProfileImageUrl());
    }
}
