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
//         1번 방법이 좀 더 DDD 스럽긴해서 고민 중입니다.
        // 1번 방법
//        CategoryEntity categoryEntity = categoryJpaRepository.findActiveCategoryById(categoryId)
//                .orElseThrow(() -> new GuildServerException(FailType.CATEGORY_NOT_FOUND));
//        categoryEntity.deactivate();

        // 2번 방법
        categoryJpaRepository.softDeleteById(categoryId);
    }
}
