import { useQuery } from '@tanstack/react-query';

import { getFriendsList } from '@/api/friends';

import * as S from './styles';

const FriendsList = () => {
  // TODO: 상태 동적으로 변경하기
  // TODO: 내 요청을 상대방이 수락할 경우 invalidate
  const { data: friends } = useQuery({ queryKey: ['friendsList'], queryFn: getFriendsList, staleTime: 10 * 60 * 1000 });
  if (!friends) return null;

  return (
    <S.FriendsList>
      <S.FriendCount>모든 친구 - {friends.length}명</S.FriendCount>
      {friends.map((friend) => (
        <S.FriendItem key={friend.userId}>
          <S.FriendProfileImage $imageUrl={friend.profileImageUrl}>
            <S.FriendStatusMark $isOnline={true} />
          </S.FriendProfileImage>
          <S.FriendInfo>
            <S.FriendName>{friend.name}</S.FriendName>
            <S.FriendStatus>온라인</S.FriendStatus>
          </S.FriendInfo>
        </S.FriendItem>
      ))}
    </S.FriendsList>
  );
};

export default FriendsList;
