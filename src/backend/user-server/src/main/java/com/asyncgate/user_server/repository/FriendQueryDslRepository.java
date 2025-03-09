package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.domain.FriendStatus;
import com.asyncgate.user_server.dto.response.FriendQueryDto;
import com.asyncgate.user_server.entity.FriendEntity;
import com.asyncgate.user_server.entity.QFriendEntity;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFriendEntity friendEntity = QFriendEntity.friendEntity;

    /**
     * 본인이 보낸 친구 요청: 요청자(requestedBy)가 userId인 경우, 상태(PENDING)와 soft delete 조건 적용
     */
    public List<FriendQueryDto> findSentFriendRequests(final String userId) {
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
    public List<FriendQueryDto> findReceivedFriendRequests(final String userId) {
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

    private List<FriendQueryDto> transFriendIds(String userId, List<FriendEntity> friendEntities) {
        return friendEntities.stream().map(
                friendEntity -> {
                    if (friendEntity.getUserId1().equals(userId)) {
                        return new FriendQueryDto(friendEntity.getId(), friendEntity.getUserId2());
                    } else {
                        return new FriendQueryDto(friendEntity.getId(), friendEntity.getUserId1());
                    }
                }
        ).toList();
    }

    /**
     * 본인의 실제 친구: 친구 관계에 포함되고 상태가 ACCEPTED인 경우 (soft delete 조건 포함)
     */
    public List<FriendQueryDto> findFriendsIdByUserId(final String userId) {
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

    public void validNotExists(final String userId1, final String userId2) {
        String lowerUserId = Friend.getLowerUserId(userId1, userId2);
        String higherUserId = Friend.getHigherUserId(userId1, userId2);

        Optional<FriendEntity> optionalFindEntity = Optional.ofNullable(
                jpaQueryFactory.select(friendEntity)
                        .from(friendEntity)
                        .where(
                                friendEntity.userId1.eq(lowerUserId),
                                friendEntity.userId2.eq(higherUserId),
                                friendEntity.deleted.isFalse()
                        )
                        .fetchOne()
        );

        if (optionalFindEntity.isEmpty()) {
            return;
        }

        FriendEntity findFriendEntity = optionalFindEntity.get();

        if (findFriendEntity.getStatus().equals(FriendStatus.ACCEPTED)) {
            throw new UserServerException(FailType.FRIEND_ALREADY_ACCEPTED_EXISTS);
        }
        if (findFriendEntity.getStatus().equals(FriendStatus.PENDING)) {
            throw new UserServerException(FailType.FRIEND_ALREADY_PENDING_EXISTS);
        }
        if (findFriendEntity.getStatus().equals(FriendStatus.REJECTED)) {
            throw new UserServerException(FailType.FRIEND_ALREADY_REJECTED_EXISTS);
        }
    }

    public FriendEntity findByIdAndPending(final String friendId) {
        FriendEntity findFriendEntity = Optional.ofNullable(
                jpaQueryFactory.select(friendEntity)
                        .from(friendEntity)
                        .where(
                                friendEntity.id.eq(friendId),
                                friendEntity.status.eq(FriendStatus.PENDING),
                                friendEntity.deleted.isFalse()
                        )
                        .fetchOne()
        ).orElseThrow(() -> new UserServerException(FailType.FRIEND_PENDING_NOT_FOUND));

        if (!findFriendEntity.getStatus().equals(FriendStatus.PENDING)) {
            throw new UserServerException(FailType.FRIEND_IS_NOT_PENDING);
        }
        return findFriendEntity;
    }
}
