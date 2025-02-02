package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;

import java.util.List;

public record GuildRandResponses(
        List<InnerGuildResponse> responses
) {

    public static GuildRandResponses from(final List<Guild> guilds) {
        List<InnerGuildResponse> innerGuilds = transInnerGuilds(guilds);
        return new GuildRandResponses(innerGuilds);
    }

    private static List<InnerGuildResponse> transInnerGuilds(final List<Guild> guilds) {
        return guilds.stream()
                .map(guild -> new InnerGuildResponse(guild.getId(), guild.getName(), guild.getProfileImageUrl()))
                .toList();
    }

    private record InnerGuildResponse(String guildId, String name, String profileImageUrl) {}
}
