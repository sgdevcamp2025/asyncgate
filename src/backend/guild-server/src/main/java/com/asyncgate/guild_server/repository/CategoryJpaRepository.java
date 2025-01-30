package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.CategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.id = :categoryId AND c.deleted = false")
    Optional<CategoryEntity> findActiveCategoryById(@Param("categoryId") String categoryId);

    @Modifying
    @Query("UPDATE CategoryEntity c SET c.deleted = true WHERE c.id = :categoryId AND c.deleted = false")
    void softDeleteById(@Param("categoryId") String categoryId);

    @Modifying
    @Query("UPDATE CategoryEntity c SET c.deleted = true WHERE c.guildId = :guildId AND c.deleted = false")
    void softDeleteAllByGuildId(@Param("guildId") String guildId);

}
