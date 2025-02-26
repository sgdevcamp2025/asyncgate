import GuildCategories from '@/components/guild/GuildCategory';

import GuildList from '../../components/guild/GuildList';

import * as S from './styles';

const FriendsPage = () => {
  return (
    <S.FriendsPage>
      <S.ContentContainer>
        <GuildList />
        <GuildCategories />
      </S.ContentContainer>
    </S.FriendsPage>
  );
};

export default FriendsPage;
