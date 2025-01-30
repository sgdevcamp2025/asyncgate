package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.Category;
import com.asyncgate.guild_server.entity.CategoryEntity;
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
    public Category getById(final String categoryId) {
        return null;
    }

    @Override
    public void deleteById(final String categoryId) {

    }
}
