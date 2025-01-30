package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.entity.GuildEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.mapper.GuildMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildRepositoryImpl implements GuildRepository {

    private final GuildJpaRepository guildJpaRepository;

    @Override
    public void save(final Guild guild) {
        GuildEntity guildEntity = GuildMapper.toEntity(guild);
        guildJpaRepository.save(guildEntity);
    }

    @Override
    public Guild getById(final String guildId) {
        GuildEntity guildEntity = guildJpaRepository.findById(guildId)
                .orElseThrow(() -> new GuildServerException(FailType.GUILD_NOT_FOUND));
        return GuildMapper.toDomain(guildEntity);
    }
}
