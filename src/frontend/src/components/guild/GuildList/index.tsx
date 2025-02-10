import CreateGuildModal from '../../../pages/FriendsPage/components/CreateGuildModal';
import useModalStore from '../../../stores/modalStore';

import * as S from './styles';

const GuildList = () => {
  const { openModal } = useModalStore();

  const handleOpenModal = () => {
    openModal('basic', 'create-server', <CreateGuildModal />);
  };

  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <S.AddServerButton onClick={handleOpenModal}>
        <S.PlusIcon size={24} />
      </S.AddServerButton>
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
