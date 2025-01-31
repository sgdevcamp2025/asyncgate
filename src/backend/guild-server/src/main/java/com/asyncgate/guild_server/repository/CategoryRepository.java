package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;

import java.util.List;

public interface CategoryRepository {
    void save(Category category);

    void deleteById(String categoryId);

    void deleteAllByGuildId(String guildId);

    List<Category> findActiveAllByGuildId(String guildId);
}
