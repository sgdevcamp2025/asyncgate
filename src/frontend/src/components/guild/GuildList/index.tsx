import { useEffect, useState } from 'react';

import CreateGuildModal from '../../../pages/FriendsPage/components/CreateGuildModal';
import CustomizeGuildModal from '../../../pages/FriendsPage/components/CustomizeGuildModal';
import useModalStore from '../../../stores/modalStore';
import { CreateGuildStep } from '../types';

import * as S from './styles';

const GuildList = () => {
  const { openModal, modal } = useModalStore();
  const [currentModal, setCurrentModal] = useState<CreateGuildStep | null>(null);

  const handleChangeModal = (modal: React.ReactNode) => {
    openModal('basic', modal);
  };

  useEffect(() => {
    if (!modal.basic) {
      setCurrentModal(null);
    }
  }, [modal]);

  useEffect(() => {
    if (currentModal === null) return;

    switch (currentModal) {
      case 'initial':
        handleChangeModal(<CreateGuildModal setCurrentModal={setCurrentModal} />);
        break;
      case 'customize':
        handleChangeModal(<CustomizeGuildModal setCurrentModal={setCurrentModal} />);
        break;
    }
  }, [currentModal]);

  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <S.AddGuildButton onClick={() => setCurrentModal('initial')}>
        <S.PlusIcon size={24} />
      </S.AddGuildButton>
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
