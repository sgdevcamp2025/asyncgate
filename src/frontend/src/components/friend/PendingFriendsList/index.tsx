import useGetReceivedRequests from './hooks/useGetReceivedRequests';
import useGetSentRequests from './hooks/useGetSentRequests';
import useHandleFriendRequest from './hooks/useHandleFriendRequest';
import * as S from './styles';

const PendingFriendsList = () => {
  const { receivedRequests } = useGetReceivedRequests();
  const { sentRequests } = useGetSentRequests();
  const { acceptRequestMutation, rejectRequestMutation, errorMessage } = useHandleFriendRequest();

  const handleAcceptButtonClick = (friendId: string) => {
    acceptRequestMutation.mutate({ friendId });
  };

  const handleRejectButtonClick = (friendId: string) => {
    rejectRequestMutation.mutate({ friendId });
  };

  return (
    <S.PendingFriendsListContainer>
      {receivedRequests && (
        <>
          <S.FriendsList>
            <S.FriendCount>받은 요청 - {receivedRequests.length}건</S.FriendCount>
            {receivedRequests.map((friend) => (
              <>
                <S.FriendItem key={friend.userId}>
                  <S.FriendInfoContainer>
                    <S.FriendProfileImage $imageUrl={''} />
                    <S.FriendNickname>{friend.nickname}</S.FriendNickname>
                  </S.FriendInfoContainer>
                  <S.ButtonContainer>
                    <S.AcceptButton
                      $isPending={acceptRequestMutation.isPending}
                      onClick={() => handleAcceptButtonClick(friend.userId)}
                    >
                      {acceptRequestMutation.isPending ? '처리 중...' : '수락하기'}
                    </S.AcceptButton>
                    <S.RejectButton
                      $isPending={rejectRequestMutation.isPending}
                      onClick={() => handleRejectButtonClick(friend.userId)}
                    >
                      {rejectRequestMutation.isPending ? '처리 중...' : '거절하기'}
                    </S.RejectButton>
                  </S.ButtonContainer>
                </S.FriendItem>
                {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
              </>
            ))}
          </S.FriendsList>
          {receivedRequests.length > 0 && <S.Divider />}
        </>
      )}
      {sentRequests && (
        <>
          <S.FriendsList>
            <S.FriendCount>보낸 요청 - {sentRequests.length}건</S.FriendCount>
            {sentRequests.map((friend) => (
              <S.FriendItem key={friend.userId}>
                <S.FriendInfoContainer>
                  <S.FriendProfileImage $imageUrl={''} />
                  <S.FriendNickname>{friend.nickname}</S.FriendNickname>
                </S.FriendInfoContainer>
              </S.FriendItem>
            ))}
          </S.FriendsList>
        </>
      )}
    </S.PendingFriendsListContainer>
  );
};

export default PendingFriendsList;
