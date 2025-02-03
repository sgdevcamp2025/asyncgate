package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildInvitationStatus;
import com.asyncgate.guild_server.entity.GuildMemberEntity;
import com.asyncgate.guild_server.entity.QGuildMemberEntity;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
                        guildMember.deleted.isFalse(),
                        guildMember.status.eq(GuildInvitationStatus.ACCEPTED)
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
                        guildMember.deleted.isFalse(),
                        guildMember.status.eq(GuildInvitationStatus.ACCEPTED)
                )
                .orderBy(guildMember.createdDate.desc())
                .fetch();
    }

    public Optional<GuildMemberEntity> findAcceptedMemberByUserIdAndGuildId(final String userId, final String guildId) {
        return findByUserIdAndGuildIdAndStatus(userId, guildId, GuildInvitationStatus.ACCEPTED);
    }

    public Optional<GuildMemberEntity> findPendingMemberByUserIdAndGuildId(final String userId, final String guildId) {
        return findByUserIdAndGuildIdAndStatus(userId, guildId, GuildInvitationStatus.PENDING);
    }

    private Optional<GuildMemberEntity> findByUserIdAndGuildIdAndStatus(final String userId, final String guildId, final GuildInvitationStatus status) {
        return Optional.ofNullable(
                queryFactory
                .selectFrom(guildMember)
                .where(
                        guildMember.userId.eq(userId),
                        guildMember.guildId.eq(guildId),
                        guildMember.deleted.isFalse(),
                        guildMember.status.eq(status)
                )
                .fetchOne()
        );
    }
}
