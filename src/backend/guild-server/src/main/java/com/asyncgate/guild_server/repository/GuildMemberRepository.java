package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.GuildMember;

public interface GuildMemberRepository {
    void save(GuildMember guildMember);

    GuildMember getByUserIdAndGuildId(String userId, String guildId);

    void deleteAllByGuildId(String guildId);
}
