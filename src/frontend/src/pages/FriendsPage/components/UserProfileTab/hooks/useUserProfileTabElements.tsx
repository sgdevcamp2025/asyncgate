import { useNavigate } from 'react-router-dom';

import useModalStore from '@/stores/modalStore';

import DeleteAccountConfirmModal from '../components/DeleteAccountConfirmModal';

interface MenuItem {
  elementId: string;
  content: string;
  handleClick: () => void;
}

const useUserProfileTabElements = () => {
  const navigate = useNavigate();
  const { openModal } = useModalStore();

  const handleModifyProfile = () => {
    // 추후 정보 수정 모달 구현
    console.log('사용자 정보 수정 메뉴 클릭');
  };

  const handleLogout = () => {
    localStorage.removeItem('access_token');
    navigate('/login', { replace: true });
  };

  const handleDeleteAccount = () => {
    openModal('withFooter', <DeleteAccountConfirmModal />);
  };

  const userProfileTabElements: MenuItem[] = [
    {
      elementId: 'modifyUserProfile',
      content: '사용자 정보 수정',
      handleClick: handleModifyProfile,
    },
    {
      elementId: 'logout',
      content: '로그아웃',
      handleClick: handleLogout,
    },
    {
      elementId: 'deleteAccount',
      content: '회원 탈퇴',
      handleClick: handleDeleteAccount,
    },
  ];

  return { userProfileTabElements };
};

export default useUserProfileTabElements;
