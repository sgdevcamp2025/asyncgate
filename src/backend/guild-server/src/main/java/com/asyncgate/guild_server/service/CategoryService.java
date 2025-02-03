package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.CategoryRequest;
import com.asyncgate.guild_server.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse create(String userId, CategoryRequest request);

    void delete(String userId, String guildId, String categoryId);
}
