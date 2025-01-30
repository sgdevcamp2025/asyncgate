package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildJpaRepository extends JpaRepository<GuildEntity, String> {
}
