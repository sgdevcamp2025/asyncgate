package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, String> {
}
