package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Friend;
import com.asyncgate.user_server.domain.FriendStatus;
import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.dto.response.FriendQueryDto;
import com.asyncgate.user_server.dto.response.FriendsResponse;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.FriendRepository;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.usecase.FriendUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService implements FriendUseCase {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Override
    public Member getByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public Friend registerFriend(final String requestUserId, final String toUserId) {
        friendRepository.validNotExists(requestUserId, toUserId);
        Friend friend = Friend.create(requestUserId, toUserId);
        friendRepository.save(friend);
        return friend;
    }

    @Override
    @Transactional
    public Friend acceptFriend(final String userId, final String friendId) {
        Friend friend = friendRepository.findIdAndPending(friendId);
        validEditPermission(userId, friend);
        friend.accept();
        friendRepository.save(friend);
        return friend;
    }

    private void validEditPermission(String userId, Friend friend) {
        validDeletePermission(userId, friend);
        // 현재 사용자가 요청을 보낸 사람(requestedBy)인 경우, 수락할 수 없음
        if (userId.equals(friend.getRequestedBy())) {
            throw new UserServerException(FailType.UNAUTHORIZED_ACTION);
        }
    }

    // 권한 체크: 현재 사용자가 friend 관계에 포함되어 있는지 확인
    private void validDeletePermission(String userId, Friend friend) {
        if (!(userId.equals(friend.getUserId1()) || userId.equals(friend.getUserId2()))) {
            throw new UserServerException(FailType.UNAUTHORIZED_ACCESS);
        }
    }

    @Override
    @Transactional
    public Friend rejectFriend(final String userId, final String friendId) {
        Friend friend = friendRepository.findIdAndPending(friendId);
        validEditPermission(userId, friend);
        friend.reject();
        friendRepository.save(friend);
        return friend;
    }

    @Override
    @Transactional
    public void deleteFriend(final String userId, final String friendId) {
        Friend friend = friendRepository.findById(friendId);
        validDeletePermission(userId, friend);
        friendRepository.deleteById(friendId);
    }

    @Override
    public FriendsResponse getSentFriendRequests(final String userId) {
        List<FriendQueryDto> sentFriendDtos = friendRepository.findSentFriendRequests(userId);
        List<String> friendIds = sentFriendDtos.stream()
                .map(FriendQueryDto::friendId)
                .toList();
        List<Member> members = memberRepository.getByMemberIds(friendIds);
        return FriendsResponse.of(sentFriendDtos, members, FriendStatus.PENDING);
    }


    @Override
    public FriendsResponse getReceivedFriendRequests(final String userId) {
        List<FriendQueryDto> receivedFriendDtos = friendRepository.findReceivedFriendRequests(userId);
        List<String> friendIds = receivedFriendDtos.stream()
                .map(FriendQueryDto::friendId)
                .toList();
        List<Member> members = memberRepository.getByMemberIds(friendIds);
        return FriendsResponse.of(receivedFriendDtos, members, FriendStatus.RECEIVED);
    }

    @Override
    public FriendsResponse getFriends(final String userId) {
        List<FriendQueryDto> friendDtos = friendRepository.findFriendIdsByUserId(userId);
        List<String> friendIds = friendDtos.stream()
                .map(FriendQueryDto::friendId)
                .toList();
        List<Member> members = memberRepository.getByMemberIds(friendIds);
        return FriendsResponse.of(friendDtos, members, FriendStatus.ACCEPTED);
    }
}
