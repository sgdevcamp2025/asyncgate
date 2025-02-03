package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuildMemberJpaRepository extends JpaRepository<GuildMemberEntity, String> {

    @Modifying
    @Query("UPDATE GuildMemberEntity g SET g.deleted = true WHERE g.guildId = :guildId")
    void softDeleteAllByGuildId(@Param("guildId") String guildId);
}
