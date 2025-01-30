package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.entity.ChannelEntity;
import com.asyncgate.guild_server.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelJpaRepository jpaRepository;

    @Override
    public void create(final Channel channel) {
        ChannelEntity channelEntity = DomainUtil.ChannelMapper.toEntity(channel);
        jpaRepository.save(channelEntity);
    }

    @Override
    public void delete(final String categoryId) {
        jpaRepository.softDeleteById(categoryId);
    }
}
