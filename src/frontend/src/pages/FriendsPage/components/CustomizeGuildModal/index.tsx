import { useState } from 'react';
import { LuCamera } from 'react-icons/lu';
import { TbPlus } from 'react-icons/tb';

import Modal from '../../../../components/common/Modal';
import { BodyMediumText, CaptionText, ChipText, SmallText } from '../../../../styles/Typography';

import * as S from './styles';

const CustomizeGuildModal = () => {
  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (value: string) => {
    setInputValue(value);
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
            <S.ImageUpLoad>
              <S.UpLoadIcon>
                <LuCamera size={24} />
                <SmallText>UPLOAD</SmallText>
              </S.UpLoadIcon>
              <S.PlusIcon>
                <TbPlus size={18} />
              </S.PlusIcon>
            </S.ImageUpLoad>
            <S.GuildNameWrapper>
              <ChipText>서버 이름</ChipText>
              <S.GuildNameInput
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => handleInputChange(e.target.value)}
                value={inputValue}
                placeholder="서버이름을 입력해주세요"
              />
              <S.Caption>서버를 만들면 Discord의 커뮤니티 지침에 동의하게 됩니다.</S.Caption>
            </S.GuildNameWrapper>
          </S.ContentContainer>
        </Modal.Content>
        <S.FooterContainer>
          <S.BackButton>뒤로가기</S.BackButton>
          <S.CreateButton>만들기</S.CreateButton>
        </S.FooterContainer>
      </Modal>
    </S.CustomizeGuildModal>
  );
};

export default CustomizeGuildModal;
