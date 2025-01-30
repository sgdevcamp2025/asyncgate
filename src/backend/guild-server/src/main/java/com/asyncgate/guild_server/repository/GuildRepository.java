package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Guild;

public interface GuildRepository {
    void save(Guild guild);

    Guild getById(String guildId);
}
