import useModalStore from '@/stores/modalStore';

import CreateGuildModalContent from '../CreateGuildModalContent';

import * as S from './styles';

const GuildList = () => {
  const { openModal } = useModalStore();

  const handleChangeModal = () => {
    openModal('basic', <CreateGuildModalContent />);
  };

  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <S.AddGuildButton onClick={handleChangeModal}>
        <S.PlusIcon size={24} />
      </S.AddGuildButton>
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
