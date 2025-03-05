import { IoMicSharp, IoMicOffSharp } from 'react-icons/io5';
import { MdHeadset, MdHeadsetOff } from 'react-icons/md';
import { RiSettings5Fill } from 'react-icons/ri';

import useDropdown from '@/hooks/useDropdown';

import UserProfileTab from '../UserProfileTab';

import useGetUserInfo from './hooks/useGetUserInfo';
import * as S from './styles';

// TODO: 마이크, 헤드셋 전역 상태로 관리
interface UserProfileProps {
  isMicOn: boolean;
  isHeadsetOn: boolean;
  handleMicToggle: () => void;
  handleHeadsetToggle: () => void;
}

const UserProfile = ({ isMicOn, isHeadsetOn, handleMicToggle, handleHeadsetToggle }: UserProfileProps) => {
  const { isOpened, dropdownRef, toggleDropdown } = useDropdown();

  const { userInfo } = useGetUserInfo();
  if (!userInfo) return null;

  return (
    <S.UserProfileContainer ref={dropdownRef}>
      <S.UserInfoContainer onClick={toggleDropdown}>
        <S.UserImage $userImageUrl={userInfo.profileImageUrl}>
          <S.UserStatusMark $isOnline={true} />
        </S.UserImage>
        <S.UserInfo>
          <S.UserName>{userInfo.nickname}</S.UserName>
          <S.UserStatus>온라인</S.UserStatus>
        </S.UserInfo>
      </S.UserInfoContainer>
      <S.ControlButtonContainer>
        <S.ControlButton onClick={handleMicToggle}>
          {isMicOn ? <IoMicSharp color="lightgray" size={20} /> : <IoMicOffSharp color="red" size={20} />}
        </S.ControlButton>
        <S.ControlButton onClick={handleHeadsetToggle}>
          {isHeadsetOn ? <MdHeadset color="lightgray" size={20} /> : <MdHeadsetOff color="red" size={20} />}
        </S.ControlButton>
        <S.ControlButton>
          <RiSettings5Fill color="lightgray" size={20} />
        </S.ControlButton>
      </S.ControlButtonContainer>
      {isOpened && <UserProfileTab />}
    </S.UserProfileContainer>
  );
};

export default UserProfile;
