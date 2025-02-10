import { useEffect, useState } from 'react';

import CreateGuildModal from '../../../pages/FriendsPage/components/CreateGuildModal';
import CustomizeGuildModal from '../../../pages/FriendsPage/components/CustomizeGuildModal';
import useModalStore from '../../../stores/modalStore';

import * as S from './styles';

export type CreateGuildStep = 'initial' | 'customize';

const GuildList = () => {
  const { openModal } = useModalStore();
  const [currentModal, setCurrentModal] = useState<CreateGuildStep | null>(null);

  const handleChangeModal = (key: string, modal: React.ReactNode) => {
    openModal('basic', key, modal);
  };

  useEffect(() => {
    if (currentModal === null) return;

    switch (currentModal) {
      case 'initial':
        handleChangeModal('create-server', <CreateGuildModal setCurrentModal={setCurrentModal} />);
        break;
      case 'customize':
        handleChangeModal('customize-server', <CustomizeGuildModal setCurrentModal={setCurrentModal} />);
        break;
    }
  }, [currentModal]);

  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <S.AddServerButton onClick={() => setCurrentModal('initial')}>
        <S.PlusIcon size={24} />
      </S.AddServerButton>
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
