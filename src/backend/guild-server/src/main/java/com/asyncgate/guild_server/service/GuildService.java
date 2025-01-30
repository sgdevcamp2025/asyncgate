package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.request.GuildUpdateRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;

public interface GuildService {
    GuildResponse create(GuildCreateRequest request);

    void delete(String guildId);

    GuildResponse update(String guildId, GuildUpdateRequest request);
}
