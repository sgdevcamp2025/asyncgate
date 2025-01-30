package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;

public interface CategoryRepository {
    void save(Category category);

    Category getById(String categoryId);

    void deleteById(String categoryId);
}
