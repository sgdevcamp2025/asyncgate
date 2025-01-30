package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildMemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuildMemberJpaRepository extends JpaRepository<GuildMemberEntity, String> {

    @Query("SELECT g FROM GuildMemberEntity g WHERE g.userId = :userId AND g.guildId = :guildId AND g.deleted = false")
    Optional<GuildMemberEntity> findActiveByUserIdAndGuildId(String userId, String guildId);

    @Modifying
    @Query("UPDATE GuildMemberEntity g SET g.deleted = true WHERE g.guildId = :guildId")
    void softDeleteAllByGuildId(@Param("guildId") String guildId);
}
