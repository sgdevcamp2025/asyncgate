package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildMember;
import com.asyncgate.guild_server.entity.GuildMemberEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildMemberRepositoryImpl implements GuildMemberRepository {

    private final GuildMemberJpaRepository jpaRepository;

    @Override
    public void save(final GuildMember guildMember) {
        GuildMemberEntity guildMemberEntity = DomainUtil.GuildMemberMapper.toEntity(guildMember);
        jpaRepository.save(guildMemberEntity);
    }

    @Override
    public GuildMember getByUserIdAndGuildId(final String userId, final String guildId) {
        GuildMemberEntity guildMemberEntity = jpaRepository.findActiveByUserIdAndGuildId(userId, guildId)
                .orElseThrow(() -> new GuildServerException(FailType.GUILD_MEMBER_NOT_FOUND));
        return DomainUtil.GuildMemberMapper.toDomain(guildMemberEntity);
    }

    @Override
    public void deleteAllByGuildId(final String guildId) {
        jpaRepository.softDeleteAllByGuildId(guildId);
    }
}
