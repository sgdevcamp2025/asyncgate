package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.ChannelEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChannelJpaRepository extends JpaRepository<ChannelEntity, String> {

    @Query("SELECT c FROM ChannelEntity c WHERE c.id = :id AND c.deleted = false")
    Optional<ChannelEntity> findActiveById(String id);

    @Modifying
    @Query("UPDATE ChannelEntity c SET c.deleted = true WHERE c.categoryId = :categoryId")
    void softDeleteById(@Param("categoryId") String categoryId);

    @Modifying
    @Query("UPDATE ChannelEntity c SET c.deleted = true WHERE c.guildId = :guildId AND c.deleted = false")
    void softDeleteAllByGuildId(@Param("guildId") String guildId);

    @Modifying
    @Query("UPDATE ChannelEntity c SET c.deleted = true WHERE c.categoryId = :categoryId AND c.deleted = false")
    void softDeleteAllByCategoryId(@Param("categoryId") String categoryId);
}
