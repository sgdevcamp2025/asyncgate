package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;
import lombok.Getter;

public record GuildResponse(String guildId, String name, boolean isPrivate, String profileImageUrl) {

    public static GuildResponse from(final Guild guild) {
        return new GuildResponse(guild.getId(), guild.getName(), guild.isPrivate(), guild.getProfileImageUrl());
    }
}
