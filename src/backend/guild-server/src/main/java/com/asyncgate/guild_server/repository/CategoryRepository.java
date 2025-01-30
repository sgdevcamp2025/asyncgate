package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;

public interface CategoryRepository {
    void save(Category category);

    void deleteById(String categoryId);

    void deleteAllByGuildId(String guildId);
}
