package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.entity.MemberEntity;
import com.asyncgate.user_server.entity.QMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberEntity memberEntity = QMemberEntity.memberEntity;

    public List<MemberEntity> getByMemberIds(final List<String> memberIds) {
        List<MemberEntity> memberEntities = jpaQueryFactory
                .select(this.memberEntity)
                .from(this.memberEntity)
                .where(
                        this.memberEntity.id.in(memberIds),
                        this.memberEntity.deleted.isFalse()
                )
                .fetch();
        return memberEntities;
    }
}
