package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.ChannelEntity;
import com.asyncgate.guild_server.entity.QChannelEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChannelQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QChannelEntity channel = QChannelEntity.channelEntity;

    public List<ChannelEntity> findActiveAllByGuildId(String guildId) {
        return queryFactory
                .select(channel)
                .from(channel)
                .where(
                        channel.guildId.eq(guildId),
                        channel.deleted.isFalse()
                )
                .fetch();
    }
}
