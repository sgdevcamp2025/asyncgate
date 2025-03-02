import { useState } from 'react';

import AuthCheckbox from '@/components/common/AuthCheckbox';
import Modal from '@/components/common/Modal';

import useGetFriendsList from '../FriendsList/hooks/useGetFriendsList';

import usePostDirect from './hooks/usePostDirect';
import * as S from './styles';

const MAX_DIRECT_MESSAGE_FRIENDS = 9;

const CreateDirectMessageModal = () => {
  const [selectedFriends, setSelectedFriends] = useState<string[]>([]);
  const { createDirectMessageMutation } = usePostDirect();
  const { friends } = useGetFriendsList();
  if (!friends) return null;

  const handleCheckboxClick = (userId: string) => {
    // 이미 선택된 친구를 누르면 선택 해제 가능
    if (selectedFriends.length === MAX_DIRECT_MESSAGE_FRIENDS) {
      if (!selectedFriends.includes(userId)) return;
    }

    setSelectedFriends((prev) => (prev.includes(userId) ? prev.filter((id) => id !== userId) : [...prev, userId]));
  };

  const handleCreateDirectMessage = async () => {
    try {
      createDirectMessageMutation.mutate({ bodyRequest: { memberIds: selectedFriends } });
    } catch (error) {
      console.log('DM 생성 실패', error);
    }
  };

  const isButtonDisabled = !selectedFriends.length || createDirectMessageMutation.isPending;

  return (
    <Modal name="withFooter">
      <Modal.Header>
        <S.ModalTitle>친구 선택하기</S.ModalTitle>
        <S.SelectedFriendsCount>
          친구를 {MAX_DIRECT_MESSAGE_FRIENDS - selectedFriends.length}명 더 선택할 수 있어요.
        </S.SelectedFriendsCount>
      </Modal.Header>
      <Modal.Content>
        <S.FriendList>
          {friends.map((friend) => (
            <S.FriendItem key={friend.userId} onClick={() => handleCheckboxClick(friend.userId)}>
              <S.FriendProfileImage $imageUrl={friend.profileImageUrl}>
                <S.FriendStatusMark $isOnline={true} />
              </S.FriendProfileImage>
              <S.FriendInfo>
                <S.FriendNickname>{friend.nickname}</S.FriendNickname>
              </S.FriendInfo>
              <AuthCheckbox isChecked={selectedFriends.includes(friend.userId)} />
            </S.FriendItem>
          ))}
        </S.FriendList>
      </Modal.Content>
      <Modal.Footer>
        <S.CreateButton disabled={isButtonDisabled} $isDisabled={isButtonDisabled} onClick={handleCreateDirectMessage}>
          {createDirectMessageMutation.isPending ? '요청 중...' : 'DM 생성'}
        </S.CreateButton>
      </Modal.Footer>
    </Modal>
  );
};

export default CreateDirectMessageModal;
