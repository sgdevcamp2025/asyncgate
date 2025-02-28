import DirectMessageCategory from '@/components/friend/DirectMessageCategory';
import GuildCategory from '@/components/guild/GuildCategory';
import VoiceChannelController from '@/components/guild/VoiceChannelController';
import UserProfile from '@/pages/FriendsPage/components/UserProfile';
import { useGuildInfoStore } from '@/stores/guildInfo';

import * as S from './styles';

const CategorySection = () => {
  const { guildId } = useGuildInfoStore();

  return (
    <S.CategorySectionContainer>
      <S.CategoryItemWrapper>{guildId ? <GuildCategory /> : <DirectMessageCategory />}</S.CategoryItemWrapper>
      <VoiceChannelController />
      <UserProfile
        userName="Fe"
        userImageUrl=""
        isOnline={true}
        isMicOn={true}
        isHeadsetOn={true}
        handleMicToggle={() => {}}
        handleHeadsetToggle={() => {}}
      />
    </S.CategorySectionContainer>
  );
};

export default CategorySection;
