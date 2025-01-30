package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;
import com.asyncgate.guild_server.entity.CategoryEntity;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.utility.DomainUtil;
import com.asyncgate.guild_server.utility.DomainUtil.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void save(final Category category) {
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category);
        categoryJpaRepository.save(categoryEntity);
    }

    @Override
    public void deleteById(final String categoryId) {
        categoryJpaRepository.softDeleteById(categoryId);
    }

    @Override
    public void deleteAllByGuildId(final String guildId) {
        categoryJpaRepository.softDeleteAllByGuildId(guildId);
    }
}
