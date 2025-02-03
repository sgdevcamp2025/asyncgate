package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Guild;

import java.util.List;

public interface GuildRepository {
    void save(Guild guild);

    Guild getById(String guildId);

    void deleteById(String guildId);

    List<Guild> getByIds(List<String> guildIds);

    List<Guild> findAllByIds(List<String> guildIds);
}
