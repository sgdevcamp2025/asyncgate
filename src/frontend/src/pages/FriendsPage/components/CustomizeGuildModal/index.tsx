import { LuCamera } from 'react-icons/lu';
import { TbPlus } from 'react-icons/tb';

import Modal from '../../../../components/common/Modal';
import { BodyMediumText, CaptionText, SmallText } from '../../../../styles/Typography';

import * as S from './styles';

const CustomizeGuildModal = () => {
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
          </S.ContentContainer>
        </Modal.Content>
        <Modal.Footer>버튼</Modal.Footer>
      </Modal>
    </S.CustomizeGuildModal>
  );
};

export default CustomizeGuildModal;
