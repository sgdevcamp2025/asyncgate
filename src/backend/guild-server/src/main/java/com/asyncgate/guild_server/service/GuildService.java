package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.request.GuildUpdateRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;

public interface GuildService {
    GuildResponse create(final GuildCreateRequest request);

    void delete();

    GuildResponse update(String guildId, GuildUpdateRequest request);
}
