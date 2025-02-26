package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.domain.Friend;

public interface FriendRepository {
    Friend findById(String id);

    Friend save(Friend entity);

    void deleteById(String friendId);
}
