import useHandleFriendRequest from './hooks/useHandleFriendRequest';
import useReceivedRequests from './hooks/useReceivedRequests';
import useSentRequests from './hooks/useSentRequests';
import * as S from './styles';

// TODO: 요청 실패 시 에러 메시지
// LOCAL_TODO: query, mutation 분리
const PendingFriendsList = () => {
  const { receivedRequests } = useReceivedRequests();
  const { sentRequests } = useSentRequests();
  const { acceptRequestMutation, rejectRequestMutation } = useHandleFriendRequest();

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
              <S.FriendItem key={friend.id}>
                <S.FriendInfoContainer>
                  <S.FriendProfileImage $imageUrl={''} />
                  <S.FriendName>{friend.id}</S.FriendName>
                </S.FriendInfoContainer>
                <S.ButtonContainer>
                  <S.AcceptButton onClick={() => handleAcceptButtonClick(friend.requestedBy)}>수락하기</S.AcceptButton>
                  <S.RejectButton onClick={() => handleRejectButtonClick(friend.requestedBy)}>거절하기</S.RejectButton>
                </S.ButtonContainer>
              </S.FriendItem>
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
              <S.FriendItem key={friend.id}>
                <S.FriendInfoContainer>
                  <S.FriendProfileImage $imageUrl={''} />
                  <S.FriendName>{friend.id}</S.FriendName>
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
