package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.response.GuildCreateResponse;

public interface GuildService {
    GuildCreateResponse create(final GuildCreateRequest request);

    void delete();

    void update();
}
