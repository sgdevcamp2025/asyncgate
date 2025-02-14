import useModalStore from '@/stores/modalStore';

import CreateGuildModalContent from '../CreateGuildModalContent';

import * as S from './styles';

const AddGuildButton = () => {
  const { openModal } = useModalStore();

  const handleChangeModal = () => {
    openModal('basic', <CreateGuildModalContent />);
  };

  return (
    <S.AddGuildButton onClick={handleChangeModal}>
      <S.PlusIcon size={24} />
    </S.AddGuildButton>
  );
};

export default AddGuildButton;
