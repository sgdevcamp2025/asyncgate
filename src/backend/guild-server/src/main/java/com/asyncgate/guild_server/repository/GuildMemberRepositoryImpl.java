package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildInvitationStatus;
import com.asyncgate.guild_server.domain.GuildMember;
import com.asyncgate.guild_server.entity.GuildMemberEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildMemberRepositoryImpl implements GuildMemberRepository {

    private final GuildMemberJpaRepository jpaRepository;
    private final GuildMemberQuerydslRepository querydslRepository;

    @Override
    public void save(final GuildMember guildMember) {
        GuildMemberEntity guildMemberEntity = DomainUtil.GuildMemberMapper.toEntity(guildMember);
        jpaRepository.save(guildMemberEntity);
    }

    @Override
    public GuildMember findAcceptedMemberByUserIdAndGuildId(final String userId, final String guildId) {
        GuildMemberEntity guildMemberEntity = querydslRepository.findAcceptedMemberByUserIdAndGuildId(userId, guildId)
                .orElseThrow(() -> new GuildServerException(FailType.GUILD_MEMBER_NOT_FOUND));
        return DomainUtil.GuildMemberMapper.toDomain(guildMemberEntity);
    }

    @Override
    public void deleteAllByGuildId(final String guildId) {
        jpaRepository.softDeleteAllByGuildId(guildId);
    }

    @Override
    public List<String> findRandGuildIdsNotJoinedByUser(final String userId, int limit) {
        return querydslRepository.findRandGuildIdsNotJoinedByUser(userId, limit);
    }

    @Override
    public List<String> findGuildIdsJoinedByUserId(final String userId) {
        return querydslRepository.findGuildIdsJoinedByUserId(userId);
    }

    @Override
    public GuildMember findByUserIdAndGuildIdAndStatus(final String userId, final String guildId, final GuildInvitationStatus status) {
        GuildMemberEntity guildMemberEntity = querydslRepository.findPendingMemberByUserIdAndGuildId(userId, guildId)
                .orElseThrow(() -> new GuildServerException(FailType.INVITATION_NOT_FOUND));
        return DomainUtil.GuildMemberMapper.toDomain(guildMemberEntity);
    }
}
