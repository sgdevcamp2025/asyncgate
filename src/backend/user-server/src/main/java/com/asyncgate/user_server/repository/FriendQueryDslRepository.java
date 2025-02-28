package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.FriendStatus;
import com.asyncgate.user_server.entity.FriendEntity;
import com.asyncgate.user_server.entity.QFriendEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFriendEntity friendEntity = QFriendEntity.friendEntity;

    /**
     * 본인이 보낸 친구 요청: 요청자(requestedBy)가 userId인 경우, 상태(PENDING)와 soft delete 조건 적용
     */
    public List<String> findSentFriendRequests(final String userId) {
        List<FriendEntity> pendingFriendEntities = jpaQueryFactory.selectFrom(friendEntity)
                .where(
                        friendEntity.requestedBy.eq(userId),
                        friendEntity.status.eq(FriendStatus.PENDING),
                        friendEntity.deleted.eq(false)
                )
                .fetch();

        return transFriendIds(userId, pendingFriendEntities);
    }

    /**
     * 본인이 받은 친구 요청: 친구 관계에 포함되면서 요청자(requestedBy)가 userId가 아닌 경우 (상태=PENDING)
     */
    public List<String> findReceivedFriendRequests(final String userId) {
        List<FriendEntity> pendingFriendEntities = jpaQueryFactory.selectFrom(friendEntity)
                .where(
                        friendEntity.status.eq(FriendStatus.PENDING),
                        friendEntity.deleted.eq(false),
                        friendEntity.userId1.eq(userId)
                                .or(friendEntity.userId2.eq(userId)),
                        friendEntity.requestedBy.ne(userId)
                )
                .fetch();

        return transFriendIds(userId, pendingFriendEntities);
    }

    private List<String> transFriendIds(String userId, List<FriendEntity> friendEntities) {
        return friendEntities.stream().map(
                friendEntity -> {
                    if (friendEntity.getUserId1().equals(userId)) {
                        return friendEntity.getUserId2();
                    } else {
                        return friendEntity.getUserId1();
                    }
                }
        ).toList();
    }

    /**
     * 본인의 실제 친구: 친구 관계에 포함되고 상태가 ACCEPTED인 경우 (soft delete 조건 포함)
     */
    public List<String> findFriendsIdByUserId(final String userId) {
        List<FriendEntity> friendEntities = jpaQueryFactory.selectFrom(friendEntity)
                .where(
                        friendEntity.status.eq(FriendStatus.ACCEPTED),
                        friendEntity.deleted.eq(false),
                        friendEntity.userId1.eq(userId)
                                .or(friendEntity.userId2.eq(userId))
                )
                .fetch();

        return transFriendIds(userId, friendEntities);
    }
}
