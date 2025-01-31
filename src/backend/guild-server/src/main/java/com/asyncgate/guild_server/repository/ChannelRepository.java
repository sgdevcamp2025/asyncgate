package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Channel;

public interface ChannelRepository {
    void save(Channel channel);

    void delete(String categoryId);

    void deleteAllByGuildId(String guildId);

    void deleteAllByCategoryId(String categoryId);

    Channel getById(String channelId);
}
