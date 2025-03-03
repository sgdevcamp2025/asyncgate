import { TiDelete } from 'react-icons/ti';

import useModalStore from '@/stores/modalStore';

import DeleteFriendConfirmModal from '../DeleteFriendConfirmModal';

import useGetFriendsList from './hooks/useGetFriendsList';
import * as S from './styles';

const FriendsList = () => {
  // TODO: 상태 동적으로 변경하기
  // TODO: 내 요청을 상대방이 수락할 경우 invalidate
  const { friends } = useGetFriendsList();
  const { openModal } = useModalStore();

  const handleDeleteFriendButtonClick = async (friendId: string, friendNickname: string) => {
    openModal('withFooter', <DeleteFriendConfirmModal friendId={friendId} friendNickname={friendNickname} />);
  };

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
              <S.IconContainer>
                <S.IconWrapper onClick={() => handleDeleteFriendButtonClick(friend.userId, friend.nickname)}>
                  <TiDelete size={28} />
                </S.IconWrapper>
              </S.IconContainer>
            </S.FriendItem>
          ))}
        </>
      )}
    </S.FriendsList>
  );
};

export default FriendsList;
