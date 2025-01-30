package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Channel;

public interface ChannelRepository {
    void create(Channel channel);

    void delete(String categoryId);

    void deleteAllByGuildId(String guildId);

    void deleteAllByCategoryId(String categoryId);

}
