package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;
import com.asyncgate.guild_server.entity.CategoryEntity;
import com.asyncgate.guild_server.support.utility.DomainUtil.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryQueryDslRepository queryDslRepository;

    @Override
    public void save(final Category category) {
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category);
        jpaRepository.save(categoryEntity);
    }

    @Override
    public void deleteById(final String categoryId) {
        jpaRepository.softDeleteById(categoryId);
    }

    @Override
    public void deleteAllByGuildId(final String guildId) {
        jpaRepository.softDeleteAllByGuildId(guildId);
    }

    @Override
    public List<Category> findActiveAllByGuildId(final String guildId) {
        return queryDslRepository.findActiveAllByGuildId(guildId).stream()
                .map(CategoryMapper::toDomain)
                .toList();
    }
}
