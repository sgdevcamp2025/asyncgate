import { useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { BiHash, BiSolidLock } from 'react-icons/bi';
import { BsFillMicFill } from 'react-icons/bs';

import { createGuildChannel } from '@/api/guild';
import Modal from '@/components/common/Modal';
import Toggle from '@/components/common/Toggle';
import useModalStore from '@/stores/modalStore';
import { BodyMediumText, ChipText, SmallText, TitleText2 } from '@/styles/Typography';
import { ChannelType, CreateChannelRequest } from '@/types/guilds';

import * as S from './styles';

interface CreateChannelModalProps {
  guildId: string;
  categoryId: string;
}

const CreateChannelModal = ({ categoryId, guildId }: CreateChannelModalProps) => {
  const { closeAllModal } = useModalStore();
  const [isPublicChannel, setIsPublicChannel] = useState(false);
  const [channelName, setChannelName] = useState('');
  const [isSelectedType, setIsSelectedType] = useState<ChannelType>(null);
  const queryClient = useQueryClient();

  const handleChannelNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChannelName(event.target.value);
  };

  const handleChangeTextType = () => {
    setIsSelectedType('TEXT');
  };

  const handleChangeVoiceType = () => {
    setIsSelectedType('VOICE');
  };

  const handleSubmit = async () => {
    try {
      const requestData: CreateChannelRequest = {
        name: channelName,
        guildId,
        categoryId,
        channelType: isSelectedType,
        private: isPublicChannel,
      };

      await createGuildChannel(requestData);

      await queryClient.invalidateQueries({ queryKey: ['guildInfo', guildId] });

      closeAllModal();
    } catch (error) {
      console.error('카테고리 생성 중 오류가 발생했어요', error);
    }
  };

  return (
    <Modal name="withFooter">
      <Modal.Header>
        <TitleText2>채널 만들기</TitleText2>
      </Modal.Header>
      <Modal.Content>
        <S.ChannelType>
          <ChipText>채널 유형</ChipText>
          <S.ChannelTypeContent $isSelectedType={isSelectedType === 'TEXT'} onClick={handleChangeTextType}>
            <BiHash size={24} />
            <S.TypeInfo>
              <BodyMediumText>텍스트</BodyMediumText>
              <SmallText>메시지, 이미지, GIF, 의견, 농담을 전송하세요</SmallText>
            </S.TypeInfo>
          </S.ChannelTypeContent>
          <S.ChannelTypeContent $isSelectedType={isSelectedType === 'VOICE'} onClick={handleChangeVoiceType}>
            <BsFillMicFill size={24} />
            <S.TypeInfo>
              <BodyMediumText>음성</BodyMediumText>
              <SmallText>음성, 영상, 화면 공유로 함께 어울리세요</SmallText>
            </S.TypeInfo>
          </S.ChannelTypeContent>
        </S.ChannelType>
        <ChipText>채널 이름</ChipText>
        <S.ChannelNameInput
          onChange={handleChannelNameChange}
          value={channelName}
          placeholder="채널 이름을 입력해주세요"
        />
        <S.PrivateSetting>
          <S.SettingText>
            <BiSolidLock size={24} />
            <BodyMediumText>비공개 채널</BodyMediumText>
          </S.SettingText>
          <Toggle isOn={isPublicChannel} onToggle={() => setIsPublicChannel((prev) => !prev)} />
        </S.PrivateSetting>
        <S.ChannelInfoText>선택한 멤버들과 역할만 이 채널을 볼 수 있어요</S.ChannelInfoText>
      </Modal.Content>
      <S.FooterContainer>
        <S.CancelButton onClick={closeAllModal}>취소</S.CancelButton>
        <S.CreateButton
          disabled={!channelName.trim()}
          $disabled={!isSelectedType || !channelName.trim()}
          onClick={handleSubmit}
        >
          채널 만들기
        </S.CreateButton>
      </S.FooterContainer>
    </Modal>
  );
};

export default CreateChannelModal;
