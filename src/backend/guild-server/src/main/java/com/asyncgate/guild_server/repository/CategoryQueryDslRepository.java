package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.CategoryEntity;
import com.asyncgate.guild_server.entity.QCategoryEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QCategoryEntity category = QCategoryEntity.categoryEntity;

    public List<CategoryEntity> findActiveAllByGuildId(final String guildId) {
        return queryFactory
                .select(category)
                .from(category)
                .where(
                        category.guildId.eq(guildId),
                        category.deleted.isFalse()
                )
                .fetch();
    }

    public boolean existsById(final String categoryId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(category)
                .where(
                        category.id.eq(categoryId),
                        category.deleted.isFalse()
                )
                .fetchFirst();

        return fetchOne != null;
    }
}
