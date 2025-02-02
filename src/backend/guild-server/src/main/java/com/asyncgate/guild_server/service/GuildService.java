package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildInfoResponse;
import com.asyncgate.guild_server.dto.response.GuildRandResponses;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.dto.response.GuildResponses;

public interface GuildService {
    GuildResponse create(String userId, GuildRequest request);

    void delete(String userId, String guildId);

    GuildResponse update(String userId, String guildId, GuildRequest request);

    GuildInfoResponse readOne(String userId, String guildId);

    GuildRandResponses readRand(String userId, int limit);

    GuildResponses readMyGuilds(String userId, int limit);
}
