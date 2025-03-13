import { useChannelActionStore } from '@/stores/channelAction';
import { useChannelInfoStore } from '@/stores/channelInfo';
import { BodyRegularText, TitleText1 } from '@/styles/Typography';

import * as S from './styles';

const VideoPage = () => {
  const { isInVoiceChannel } = useChannelActionStore();
  const { selectedChannel } = useChannelInfoStore();

  return (
    <S.VideoPage>
      {isInVoiceChannel ? (
        <>참여시 비디오들</>
      ) : (
        <S.EmptyParticipant>
          <TitleText1>{selectedChannel?.name}</TitleText1>
          <BodyRegularText>현재 음성 채널에 아무도 없어요</BodyRegularText>
        </S.EmptyParticipant>
      )}
    </S.VideoPage>
  );
};

export default VideoPage;
