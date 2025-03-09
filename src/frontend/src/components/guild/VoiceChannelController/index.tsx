import { BsFillTelephoneXFill } from 'react-icons/bs';

import { useChannelActionStore } from '@/stores/channelAction';
import { useChannelInfoStore } from '@/stores/channelInfo';
import { useGuildInfoStore } from '@/stores/guildInfo';
import { useWebRTCStore } from '@/stores/webRTCStore';
import { tokenAxios } from '@/utils/axios';

import VoiceChannelActions from '../VoiceChannelActions';

import * as S from './styles';

const VoiceChannelController = () => {
  const { selectedChannel } = useChannelInfoStore();
  const { setIsInVoiceChannel } = useChannelActionStore();
  const { guildName } = useGuildInfoStore();
  const { setIsStompConnected, disconnectStomp } = useWebRTCStore();

  const roomId = useChannelInfoStore((state) => state.selectedChannel?.name);

  const handleLeaveRoom = async () => {
    setIsInVoiceChannel(false);
    if (!roomId) {
      alert('방 ID를 입력해주세요!');
      return;
    }

    try {
      const response = await tokenAxios.delete(`https://api.jungeunjipi.com/room/${roomId}/leave`);
      console.log('방 나가기 성공: ', response);

      setIsInVoiceChannel(false);
      setIsStompConnected(false);

      disconnectStomp();
    } catch (error) {
      console.error('🚨 방 나가기 오류:', error);
    }
  };

  return (
    <S.VoiceChannelController>
      <S.ConnectStatusWrapper>
        <S.InfoText>
          <S.ConnectStatusText>음성 연결됨</S.ConnectStatusText>
          <S.ChannelInfoText>
            {selectedChannel?.name} / {guildName}
          </S.ChannelInfoText>
        </S.InfoText>
        <BsFillTelephoneXFill size={20} onClick={handleLeaveRoom} />
      </S.ConnectStatusWrapper>
      <VoiceChannelActions />
    </S.VoiceChannelController>
  );
};

export default VoiceChannelController;
