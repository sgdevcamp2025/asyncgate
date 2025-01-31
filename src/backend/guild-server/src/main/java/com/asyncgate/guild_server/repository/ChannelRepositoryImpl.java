package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.entity.ChannelEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelJpaRepository jpaRepository;
    private final ChannelQueryDslRepository queryDslRepository;

    @Override
    public void save(final Channel channel) {
        ChannelEntity channelEntity = DomainUtil.ChannelMapper.toEntity(channel);
        jpaRepository.save(channelEntity);
    }

    @Override
    public void delete(final String categoryId) {
        jpaRepository.softDeleteById(categoryId);
    }

    @Override
    public void deleteAllByGuildId(final String guildId) {
        jpaRepository.softDeleteAllByGuildId(guildId);
    }

    @Override
    public void deleteAllByCategoryId(final String categoryId) {
        jpaRepository.softDeleteAllByCategoryId(categoryId);
    }

    @Override
    public Channel getById(final String channelId) {
        ChannelEntity channelEntity = jpaRepository.findActiveById(channelId)
                .orElseThrow(() -> new GuildServerException(FailType.CHANNEL_NOT_FOUND));
        return DomainUtil.ChannelMapper.toDomain(channelEntity);
    }

    @Override
    public List<Channel> findActiveAllByGuildId(final String guildId) {
        return queryDslRepository.findActiveAllByGuildId(guildId).stream()
                .map(DomainUtil.ChannelMapper::toDomain)
                .toList();
    }

}
