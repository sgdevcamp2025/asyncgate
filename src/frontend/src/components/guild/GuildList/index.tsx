import AddGuildButton from '../AddGuildButton';

import * as S from './styles';

const GuildList = () => {
  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <AddGuildButton />
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
