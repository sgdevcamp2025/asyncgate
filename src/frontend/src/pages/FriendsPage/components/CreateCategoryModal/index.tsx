import { useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { BiSolidLock } from 'react-icons/bi';

import { createGuildCategory } from '@/api/guild';
import Modal from '@/components/common/Modal';
import Toggle from '@/components/common/Toggle';
import useModalStore from '@/stores/modalStore';
import { BodyMediumText, ChipText, TitleText2 } from '@/styles/Typography';
import { CreateCategoryRequest } from '@/types/guilds';

import * as S from './styles';

interface CreateCategoryModalProps {
  guildId: string;
}

const CreateCategoryModal = ({ guildId }: CreateCategoryModalProps) => {
  const { closeAllModal } = useModalStore();
  const [isPublicCategory, setIsPublicCategory] = useState(false);
  const [categoryName, setCategoryName] = useState('');
  const queryClient = useQueryClient();

  const handleCategoryNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCategoryName(event.target.value);
  };

  const handleSubmit = async () => {
    try {
      const requestData: CreateCategoryRequest = {
        name: categoryName,
        guildId,
        private: isPublicCategory,
      };

      await createGuildCategory(requestData);

      await queryClient.invalidateQueries({ queryKey: ['guildInfo', guildId] });
      closeAllModal();
    } catch (error) {
      console.error('카테고리 생성 중 오류가 발생했어요', error);
    }
  };

  return (
    <Modal name="withFooter">
      <Modal.Header>
        <TitleText2>카테고리 만들기</TitleText2>
      </Modal.Header>
      <Modal.Content>
        <ChipText>카테고리 이름</ChipText>
        <S.CategoryNameInput
          onChange={handleCategoryNameChange}
          value={categoryName}
          placeholder="서버이름을 입력해주세요"
        />
        <S.PrivateSetting>
          <S.SettingText>
            <BiSolidLock size={24} />
            <BodyMediumText>비공개 카테고리</BodyMediumText>
          </S.SettingText>
          <Toggle isOn={isPublicCategory} onToggle={() => setIsPublicCategory((prev) => !prev)} />
        </S.PrivateSetting>
        <S.CategoryInfoText>
          카테고리를 비공개로 만들면 선택한 멤버들과 역할만 이 카테고리를 볼 수 있어요. 이 설정은 이 카테고리에 동기화된
          채널들에도 자동으로 적용돼요.
        </S.CategoryInfoText>
      </Modal.Content>
      <S.FooterContainer>
        <S.CancelButton onClick={closeAllModal}>취소</S.CancelButton>
        <S.CreateButton disabled={!categoryName.trim()} $disabled={!categoryName.trim()} onClick={handleSubmit}>
          카테고리 만들기
        </S.CreateButton>
      </S.FooterContainer>
    </Modal>
  );
};

export default CreateCategoryModal;
