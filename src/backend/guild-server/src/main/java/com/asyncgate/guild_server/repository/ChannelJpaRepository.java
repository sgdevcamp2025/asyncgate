package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChannelJpaRepository extends JpaRepository<ChannelEntity, String> {

    @Modifying
    @Query("UPDATE CategoryEntity c SET c.deleted = true WHERE c.id = :categoryId")
    void softDeleteById(@Param("categoryId") String categoryId);

}
