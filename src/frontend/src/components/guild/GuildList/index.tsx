import { useQuery } from '@tanstack/react-query';

import { getGuilds } from '@/api/guild';
import { useGuildInfoStore } from '@/stores/guildInfo';
import useModalStore from '@/stores/modalStore';
import { GuildResponse } from '@/types/guilds';

import CreateGuildModalContent from '../CreateGuildModalContent';

import * as S from './styles';

const GuildList = () => {
  const { openModal } = useModalStore();
  const { setGuildId } = useGuildInfoStore();

  const { data } = useQuery<GuildResponse[]>({ queryKey: ['guildList'], queryFn: getGuilds });

  const handleChangeModal = () => {
    openModal('basic', <CreateGuildModalContent />);
  };

  return (
    <S.GuildList>
      <S.DMButton onClick={() => setGuildId('')}>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {data?.map((guild) => (
        <S.GuildButton
          key={guild.guildId}
          data-tooltip={guild.name}
          $imageUrl={guild.profileImageUrl}
          onClick={() => setGuildId(guild.guildId)}
        />
      ))}
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
