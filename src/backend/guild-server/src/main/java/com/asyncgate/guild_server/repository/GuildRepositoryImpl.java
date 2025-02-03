package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.entity.GuildEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildRepositoryImpl implements GuildRepository {

    private final GuildJpaRepository jpaRepository;
    private final GuildQuerydslRepository querydslRepository;

    @Override
    public void save(final Guild guild) {
        GuildEntity guildEntity = DomainUtil.GuildMapper.toEntity(guild);
        jpaRepository.save(guildEntity);
    }

    @Override
    public Guild getById(final String guildId) {
        GuildEntity guildEntity = getActiveGuildEntityById(guildId);
        return DomainUtil.GuildMapper.toDomain(guildEntity);
    }

    @Override
    public void deleteById(final String guildId) {
        jpaRepository.softDeleteById(guildId);
    }

    @Override
    public List<Guild> getByIds(final List<String> guildIds) {
        return jpaRepository.findAllById(guildIds).stream()
                .map(DomainUtil.GuildMapper::toDomain)
                .toList();
    }

    private GuildEntity getActiveGuildEntityById(final String guildId) {
        return jpaRepository.findActiveGuildById(guildId)
                .orElseThrow(() -> new GuildServerException(FailType.GUILD_NOT_FOUND));
    }

    @Override
    public List<Guild> findAllByIds(final List<String> guildIds) {
        return querydslRepository.findAllByIds(guildIds).stream()
                .map(DomainUtil.GuildMapper::toDomain)
                .toList();
    }

}
