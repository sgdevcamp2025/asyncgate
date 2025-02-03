package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;

import java.util.List;

public record GuildResponses(
        List<InnerGuildResponse> responses
) {

    public static GuildResponses from(final List<Guild> guilds) {
        List<InnerGuildResponse> innerGuilds = transInnerGuilds(guilds);
        return new GuildResponses(innerGuilds);
    }

    private static List<InnerGuildResponse> transInnerGuilds(final List<Guild> guilds) {
        return guilds.stream()
                .map(
                        guild -> new InnerGuildResponse(
                                guild.getId(), guild.getName(), guild.getProfileImageUrl()
                        )
                )
                .toList();
    }

    private record InnerGuildResponse(String guildId, String name, String profileImageUrl) {}
}