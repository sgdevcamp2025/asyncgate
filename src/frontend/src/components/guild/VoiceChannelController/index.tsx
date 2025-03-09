import { BsFillTelephoneXFill } from 'react-icons/bs';

import { useChannelActionStore } from '@/stores/channelAction';
import { useChannelInfoStore } from '@/stores/channelInfo';
import { useGuildInfoStore } from '@/stores/guildInfo';

import VoiceChannelActions from '../VoiceChannelActions';

import * as S from './styles';

const VoiceChannelController = () => {
  const { selectedChannel } = useChannelInfoStore();
  const { setIsInVoiceChannel } = useChannelActionStore();
  const { guildName } = useGuildInfoStore();

  return (
    <S.VoiceChannelController>
      <S.ConnectStatusWrapper>
        <S.InfoText>
          <S.ConnectStatusText>음성 연결됨</S.ConnectStatusText>
          <S.ChannelInfoText>
            {selectedChannel?.name} / {guildName}
          </S.ChannelInfoText>
        </S.InfoText>
        <BsFillTelephoneXFill size={20} onClick={() => setIsInVoiceChannel(false)} />
      </S.ConnectStatusWrapper>
      <VoiceChannelActions />
    </S.VoiceChannelController>
  );
};

export default VoiceChannelController;
