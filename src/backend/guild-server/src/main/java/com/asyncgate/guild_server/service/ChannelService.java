package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.ChannelCreateRequest;
import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;

public interface ChannelService {

    ChannelResponse create(String userId, ChannelCreateRequest request);

    void delete(String userId, String guildId, String categoryId, String channelId);

    ChannelResponse update(String userId, String guildId, String categoryId, String channelId, ChannelUpdateRequest request);
}
