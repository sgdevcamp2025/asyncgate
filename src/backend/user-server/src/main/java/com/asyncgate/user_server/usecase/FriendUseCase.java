package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.FriendsResponse;
import com.asyncgate.user_server.dto.response.UserClientInfoResponses;

public interface FriendUseCase {
    Member getByEmail(String email);

    Friend registerFriend(String requestUserId, String toUserId);

    Friend acceptFriend(String userId, String friendId);

    Friend rejectFriend(String userId, String friendId);

    void deleteFriend(String userId, String friendId);

    FriendsResponse getFriends(String userId);

    FriendsResponse getSentFriendRequests(String userId);

    FriendsResponse getReceivedFriendRequests(String userId);

}