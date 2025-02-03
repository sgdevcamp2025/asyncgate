package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildMember;

import java.util.List;

public interface GuildMemberRepository {
    void save(GuildMember guildMember);

    GuildMember getByUserIdAndGuildId(String userId, String guildId);

    void deleteAllByGuildId(String guildId);

    List<String> findRandGuildIdsNotJoinedByUser(String userId, int limit);

    List<String> findGuildIdsJoinedByUserId(String userId);
}
