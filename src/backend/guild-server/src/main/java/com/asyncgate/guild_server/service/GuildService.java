package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;

public interface GuildService {
    GuildResponse create(String userId, GuildRequest request);

    void delete(String userId, String guildId);

    GuildResponse update(String userId, String guildId, GuildRequest request);
}
