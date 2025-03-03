import { useQueryClient } from '@tanstack/react-query';

import { deleteFriend } from '@/api/friends';
import Modal from '@/components/common/Modal';
import useModalStore from '@/stores/modalStore';

import * as S from './styles';

interface DeleteFriendConfirmModalProps {
  friendId: string;
  friendNickname: string;
}

const DeleteFriendConfirmModal = ({ friendId, friendNickname }: DeleteFriendConfirmModalProps) => {
  const queryClient = useQueryClient();
  const { closeAllModal } = useModalStore();

  const handleDeleteFriend = async () => {
    try {
      await deleteFriend({ friendId });
      queryClient.invalidateQueries({ queryKey: ['friendsList'] });
    } catch (error) {
      console.error('친구 삭제 실패', error);
    }
  };

  return (
    <Modal name="withFooter">
      <Modal.Content>
        <S.ConfirmText>
          정말 <strong>{friendNickname}</strong> 님을 친구에서 삭제하시겠어요?
        </S.ConfirmText>
      </Modal.Content>
      <S.ModalButtonContainer>
        <S.CancelButton onClick={closeAllModal}>취소</S.CancelButton>
        <S.DeleteButton onClick={handleDeleteFriend}>친구 삭제하기</S.DeleteButton>
      </S.ModalButtonContainer>
    </Modal>
  );
};

export default DeleteFriendConfirmModal;
