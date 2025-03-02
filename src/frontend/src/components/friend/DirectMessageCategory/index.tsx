import { MdPerson } from 'react-icons/md';

import { useChannelInfoStore } from '@/stores/channelInfo';
import { BodyMediumText } from '@/styles/Typography';

import DirectMessagesList from '../DirectMessagesList';

import * as S from './styles';

const DirectMessageCategory = () => {
  const { selectedDMChannel, setSelectedDMChannel } = useChannelInfoStore();

  const handleFriendsTabClick = () => {
    setSelectedDMChannel(null);
  };

  return (
    <>
      <S.FriendTitle>
        <S.TitleName>다이렉트 메시지</S.TitleName>
      </S.FriendTitle>
      <S.TabList>
        <S.TabItem $isSelected={!selectedDMChannel} onClick={handleFriendsTabClick}>
          <S.TabIcon>
            <MdPerson size={28} />
          </S.TabIcon>
          <BodyMediumText>친구</BodyMediumText>
        </S.TabItem>
      </S.TabList>
      <DirectMessagesList />
    </>
  );
};

export default DirectMessageCategory;
