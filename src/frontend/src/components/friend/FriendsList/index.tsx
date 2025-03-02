import useGetFriendsList from './hooks/useGetFriendsList';
import * as S from './styles';

const FriendsList = () => {
  // TODO: 상태 동적으로 변경하기
  // TODO: 내 요청을 상대방이 수락할 경우 invalidate
  const { friends } = useGetFriendsList();

  return (
    <S.FriendsList>
      {friends && (
        <>
          <S.FriendCount>모든 친구 - {friends.length}명</S.FriendCount>
          {friends.map((friend) => (
            <S.FriendItem key={friend.userId}>
              <S.FriendProfileImage $imageUrl={friend.profileImageUrl}>
                <S.FriendStatusMark $isOnline={true} />
              </S.FriendProfileImage>
              <S.FriendInfo>
                <S.FriendNickname>{friend.nickname}</S.FriendNickname>
                <S.FriendStatus>온라인</S.FriendStatus>
              </S.FriendInfo>
            </S.FriendItem>
          ))}
        </>
      )}
    </S.FriendsList>
  );
};

export default FriendsList;
