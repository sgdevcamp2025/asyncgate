package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.entity.common.BaseEntity;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {

    private final FriendJpaRepository friendJpaRepository;
    private final FriendQueryDslRepository queryDslRepository;

    @Override
    public Friend findById(final String id) {
        return DomainUtil.FriendMapper.toDomain(
                friendJpaRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(
                                () -> new UserServerException(FailType.FRIEND_NOT_FOUND)
                        )
        );
    }

    @Override
    public Friend save(final Friend domain) {
        friendJpaRepository.save(DomainUtil.FriendMapper.toEntity(domain));
        return domain;
    }

    @Override
    public void deleteById(final String friendId) {
        friendJpaRepository.findById(friendId)
                .ifPresent(
                        BaseEntity::deactivate
                );
    }

    @Override
    public List<String> findSentFriendRequests(final String userId) {
        return queryDslRepository.findSentFriendRequests(userId);
    }

    @Override
    public List<String> findReceivedFriendRequests(final String userId) {
        return queryDslRepository.findReceivedFriendRequests(userId);
    }

    @Override
    public List<String> findFriendIdsByUserId(final String userId) {
        return queryDslRepository.findFriendsIdByUserId(userId);
    }
}
