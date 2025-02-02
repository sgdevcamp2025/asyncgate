package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;

import java.util.List;

public record GuildResponses(
        List<InnerGuildResponse> responses,
        boolean isFirst,
        boolean isLast,
        boolean hasNext
) {

    public static GuildResponses of(final List<Guild> guilds, int limit) {
        boolean hasNext = guilds.size() > limit; // limit보다 크면 다음 페이지 존재
        List<Guild> actualGuilds = hasNext ? guilds.subList(0, limit) : guilds;

        List<InnerGuildResponse> innerGuilds = transInnerGuilds(actualGuilds);
        return new GuildResponses(innerGuilds, true, !hasNext, hasNext);
    }

    private static List<InnerGuildResponse> transInnerGuilds(final List<Guild> guilds) {
        return guilds.stream()
                .map(guild -> new InnerGuildResponse(guild.getId(), guild.getName(), guild.getProfileImageUrl()))
                .toList();
    }

    private record InnerGuildResponse(String guildId, String name, String profileImageUrl) {}
}