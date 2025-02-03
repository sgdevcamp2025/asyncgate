package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildInvitationStatus;
import com.asyncgate.guild_server.domain.GuildMember;

import java.util.List;

public interface GuildMemberRepository {
    void save(GuildMember guildMember);

    GuildMember findAcceptedMemberByUserIdAndGuildId(String userId, String guildId);

    void deleteAllByGuildId(String guildId);

    List<String> findRandGuildIdsNotJoinedByUser(String userId, int limit);

    List<String> findGuildIdsJoinedByUserId(String userId);

    GuildMember findByUserIdAndGuildIdAndStatus(String userId, String guildId, GuildInvitationStatus status);
}
