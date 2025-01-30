package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildRepositoryImpl implements GuildRepository {

    private final GuildJpaRepository guildJpaRepository;

    @Override
    public void create(final GuildEntity guildEntity) {
        guildJpaRepository.save(guildEntity);
    }
}
