package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuildJpaRepository extends JpaRepository<GuildEntity, String> {

    @Query("SELECT g FROM GuildEntity g WHERE g.id = :guildId AND g.deleted = false")
    Optional<GuildEntity> findActiveGuildById(@Param("guildId") String guildId);

    @Modifying
    @Query("UPDATE GuildEntity g SET g.deleted = true WHERE g.id = :guildId AND g.deleted = false")
    void softDeleteById(@Param("guildId") String guildId);
}
