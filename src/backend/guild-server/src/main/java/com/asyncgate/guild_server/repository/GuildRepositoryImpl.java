package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.entity.GuildEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildRepositoryImpl implements GuildRepository {

    private final GuildJpaRepository guildJpaRepository;

    @Override
    public void save(final Guild guild) {
        GuildEntity guildEntity = DomainUtil.GuildMapper.toEntity(guild);
        guildJpaRepository.save(guildEntity);
    }

    @Override
    public Guild getById(final String guildId) {
        GuildEntity guildEntity = getActiveGuildEntityById(guildId);
        return DomainUtil.GuildMapper.toDomain(guildEntity);
    }

    @Override
    public void deleteById(final String guildId) {
        GuildEntity guildEntity = getActiveGuildEntityById(guildId);
        guildEntity.deactivate();
        guildJpaRepository.save(guildEntity);
    }

    private GuildEntity getActiveGuildEntityById(final String guildId) {
        return guildJpaRepository.findActiveGuildById(guildId)
                .orElseThrow(() -> new GuildServerException(FailType.GUILD_NOT_FOUND));
    }
}
