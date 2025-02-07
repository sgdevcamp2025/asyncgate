import * as S from './styles';

const GuildList = () => {
  return (
    <S.GuildList>
      <S.DMButton>
        <S.DiscordIcon size={32} />
      </S.DMButton>
      {/* 서버 리스트 추가 예정 */}
      <S.AddServerButton>
        <S.PlusIcon size={24} />
      </S.AddServerButton>
      <S.SearchCommunityButton>
        <S.CompassIcon size={36} />
      </S.SearchCommunityButton>
    </S.GuildList>
  );
};

export default GuildList;
