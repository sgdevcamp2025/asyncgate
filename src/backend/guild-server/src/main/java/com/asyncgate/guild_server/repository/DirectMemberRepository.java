package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.DirectMember;

import java.util.List;

public interface DirectMemberRepository {
    void saveAll(List<DirectMember> directMembers);

    List<DirectMember> getDirectMessageList(String currentUserId);

    List<String> getDirectMemberIds(String directId);
}
