package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.QGuildMemberEntity;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildMemberQuerydslRepository {

    private final JPAQueryFactory queryFactory;
    private final QGuildMemberEntity guildMember = QGuildMemberEntity.guildMemberEntity;

    public List<String> findRandGuildIdsNotJoinedByUser(final String userId, final long limit) {
        return queryFactory
                .select(guildMember.guildId)
                .from(guildMember)
                .where(
                        guildMember.userId.ne(userId),
                        guildMember.deleted.isFalse()
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(limit)
                .fetch();
    }

    public List<String> findGuildIdsJoinedByUserId(final String userId) {
        return queryFactory
                .select(guildMember.guildId)
                .from(guildMember)
                .where(
                        guildMember.userId.eq(userId),
                        guildMember.deleted.isFalse()
                )
                .orderBy(guildMember.createdDate.desc())
                .fetch();
    }

}
