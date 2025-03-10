package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.entity.DirectMemberEntity;
import com.asyncgate.guild_server.entity.QDirectMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DirectMemberQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QDirectMemberEntity directMemberEntity = QDirectMemberEntity.directMemberEntity;

    public List<DirectMemberEntity> getDirectMessageList(final String currentUserId) {
        // Sub Query: 현재 사용자가 참여한 directId 목록
        var subquery = queryFactory
                .select(directMemberEntity.directId)
                .from(directMemberEntity)
                .where(
                        directMemberEntity.memberId.eq(currentUserId),
                        directMemberEntity.deleted.isFalse()
                );

        // Main Query: 위 directId에 속한 모든 DirectMember 조회
        return queryFactory
                .selectFrom(directMemberEntity)
                .where(
                        directMemberEntity.directId.in(subquery)
                )
                .fetch();
    }

    public List<String> getMemberIdsByDirectId(final String directId) {
        return queryFactory
                .select(directMemberEntity.memberId)
                .from(directMemberEntity)
                .where(
                        directMemberEntity.directId.eq(directId),
                        directMemberEntity.deleted.isFalse()
                )
                .fetch();
    }
}
