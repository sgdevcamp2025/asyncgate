import { useNavigate } from 'react-router-dom';

import { deleteAccount } from '@/api/users';
import Modal from '@/components/common/Modal';
import useModalStore from '@/stores/modalStore';
import { useUserInfoStore } from '@/stores/userInfo';

import * as S from './styles';

const DeleteAccountConfirmModal = () => {
  const navigate = useNavigate();
  const { closeAllModal } = useModalStore();
  const { userInfo } = useUserInfoStore();
  if (!userInfo) return null;

  const handleDeleteAccount = async () => {
    try {
      await deleteAccount({ userId: userInfo.userId });
      closeAllModal();
      navigate('/login', { replace: true });
    } catch (error) {
      console.error('회원 탈퇴 실패', error);
    }
  };

  return (
    <Modal name="withFooter">
      <Modal.Content>
        <S.ConfirmText>정말 계정을 탈퇴하시겠어요?</S.ConfirmText>
      </Modal.Content>
      <S.ModalButtonContainer>
        <S.CancelButton onClick={closeAllModal}>취소</S.CancelButton>
        <S.DeleteButton onClick={handleDeleteAccount}>탈퇴하기</S.DeleteButton>
      </S.ModalButtonContainer>
    </Modal>
  );
};

export default DeleteAccountConfirmModal;
