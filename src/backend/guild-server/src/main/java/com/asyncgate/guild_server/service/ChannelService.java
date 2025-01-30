package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.ChannelRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;

public interface ChannelService {

    ChannelResponse create(String userId, ChannelRequest request);

    void delete(String userId, String guildId, String categoryId, String channelId);
}
