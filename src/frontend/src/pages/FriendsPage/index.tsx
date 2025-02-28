import CategorySection from '@/pages/FriendsPage/components/CategorySection';
import { useGuildInfoStore } from '@/stores/guildInfo';

import GuildList from '../../components/guild/GuildList';
import VideoPage from '../VideoPage';

import ChattingSection from './components/ChattingSection';
import * as S from './styles';

const FriendsPage = () => {
  const { guildId } = useGuildInfoStore();

  const renderCategoryComponent = () => {
    const channelInfoStr = localStorage.getItem('channelInfo');

    if (!guildId) return <ChattingSection />;

    if (channelInfoStr) {
      const channelData = JSON.parse(channelInfoStr);
      const channelType = channelData.state.selectedChannel.type;

      if (channelType === 'VOICE') return <VideoPage />;

      // 텍스트 채널시 채팅창 주가 예정
    }
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
