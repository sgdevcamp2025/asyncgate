import CategorySection from '@/pages/FriendsPage/components/CategorySection';
import { useChannelInfoStore } from '@/stores/channelInfo';
import { useGuildInfoStore } from '@/stores/guildInfo';

import GuildList from '../../components/guild/GuildList';
import VideoPage from '../VideoPage';

import ChattingSection from './components/ChattingSection';
import * as S from './styles';

const FriendsPage = () => {
  const { guildId } = useGuildInfoStore();
  const { selectedChannel } = useChannelInfoStore();

  const renderCategoryComponent = () => {
    if (!guildId) return <ChattingSection />;

    if (!selectedChannel) return <ChattingSection />;

    if (selectedChannel.type === 'VOICE') return <VideoPage />;

    return <ChattingSection />;
  };

  return (
    <S.FriendsPage>
      <S.ContentContainer>
        <GuildList />
        <CategorySection />
        {renderCategoryComponent()}
      </S.ContentContainer>
    </S.FriendsPage>
  );
};

export default FriendsPage;
