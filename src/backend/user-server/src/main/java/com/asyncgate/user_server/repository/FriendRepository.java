package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.dto.response.FriendQueryDto;

import java.util.List;

public interface FriendRepository {
    Friend findById(String id);

    Friend save(Friend entity);

    void deleteById(String friendId);

    List<FriendQueryDto> findSentFriendRequests(String requestedUserId);

    List<FriendQueryDto> findReceivedFriendRequests(String userId);

    List<FriendQueryDto> findFriendIdsByUserId(String userId);

    void validNotExists(String userId1, String userId2);

    Friend findIdAndPending(String friendId);
}
