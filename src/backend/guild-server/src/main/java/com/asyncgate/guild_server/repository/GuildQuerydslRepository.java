package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildEntity;
import com.asyncgate.guild_server.entity.QGuildEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildQuerydslRepository {

    private final JPAQueryFactory queryFactory;
    private final QGuildEntity guild = QGuildEntity.guildEntity;

    public List<GuildEntity> findAllByIds(List<String> guildIds) {
        return queryFactory
                .select(guild)
                .from(guild)
                .where(
                        guild.id.in(guildIds),
                        guild.deleted.isFalse()
                ).fetch();
    }

}
