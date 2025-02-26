import { useQueryClient } from '@tanstack/react-query';
import { LuCamera } from 'react-icons/lu';
import { TbPlus } from 'react-icons/tb';

import { createGuild } from '@/api/guild';
import useModalStore from '@/stores/modalStore';
import { CreateGuildRequest } from '@/types/guilds';

import Modal from '../../../../components/common/Modal';
import { BodyMediumText, CaptionText, ChipText, SmallText } from '../../../../styles/Typography';

import * as S from './styles';

interface CustomizeGuildModalProps {
  onPrev: () => void;
  guildName: string;
  isPublicGuild: boolean | null;
  profileImage: File | null;
  profileImagePreview: string | null;
  handleGuildNameChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleProfileImageChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const CustomizeGuildModal = ({
  onPrev,
  profileImage,
  profileImagePreview,
  guildName,
  isPublicGuild,
  handleGuildNameChange,
  handleProfileImageChange,
}: CustomizeGuildModalProps) => {
  const queryClient = useQueryClient();
  const { closeAllModal } = useModalStore();

  const handleSubmit = async () => {
    try {
      const requestData: CreateGuildRequest = {
        name: guildName.trim(),
        profileImage: profileImage || null,
        isPrivate: isPublicGuild === true ? false : true,
      };

      await createGuild(requestData);

      queryClient.invalidateQueries({ queryKey: ['guildList'] });

      closeAllModal();
    } catch (error) {
      console.error('길드 생성 중 오류가 발생했어요', error);
    }
  };

  return (
    <S.CustomizeGuildModal>
      <Modal name="basic">
        <Modal.Header>
          <BodyMediumText>서버 커스터마이즈하기</BodyMediumText>
        </Modal.Header>
        <Modal.Content>
          <S.ContentContainer>
            <CaptionText>
              새로운 서버에 이름과 아이콘을 부여해 개성을 드러내 보세요 나중에 언제든 바꿀 수 있어요
            </CaptionText>
            <input
              type="file"
              id="profile-image-upload"
              accept="image/*"
              onChange={handleProfileImageChange}
              style={{ display: 'none' }}
            />
            <label htmlFor="profile-image-upload">
              <S.ImageUpLoad $profileImagePreview={profileImagePreview}>
                {!profileImagePreview && (
                  <>
                    <S.UpLoadIcon>
                      <LuCamera size={24} />
                      <SmallText>UPLOAD</SmallText>
                    </S.UpLoadIcon>
                    <S.PlusIcon>
                      <TbPlus size={18} />
                    </S.PlusIcon>
                  </>
                )}
              </S.ImageUpLoad>
            </label>
            <S.GuildNameWrapper>
              <ChipText>서버 이름</ChipText>
              <S.GuildNameInput
                onChange={handleGuildNameChange}
                value={guildName}
                placeholder="서버이름을 입력해주세요"
              />
              <S.Caption>서버를 만들면 Discord의 커뮤니티 지침에 동의하게 됩니다.</S.Caption>
            </S.GuildNameWrapper>
          </S.ContentContainer>
        </Modal.Content>
        <S.FooterContainer>
          <S.BackButton onClick={onPrev}>뒤로가기</S.BackButton>
          <S.CreateButton onClick={handleSubmit}>만들기</S.CreateButton>
        </S.FooterContainer>
      </Modal>
    </S.CustomizeGuildModal>
  );
};

export default CustomizeGuildModal;
