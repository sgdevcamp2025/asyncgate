import { BiSolidVideo, BiSolidVideoOff } from 'react-icons/bi';
import { LuScreenShare } from 'react-icons/lu';
import { TbConfetti, TbTriangleSquareCircle } from 'react-icons/tb';

import { useChannelActionStore } from '@/stores/channelAction';

import * as S from './styles';

const VoiceChannelActions = () => {
  const { isInVoiceChannel } = useChannelActionStore();

  const actions = {
    video: isInVoiceChannel ? <BiSolidVideo size={24} /> : <BiSolidVideoOff size={24} />,
    screenSharing: <LuScreenShare size={24} />,
    startActions: <TbTriangleSquareCircle size={24} />,
    soundBoard: <TbConfetti size={24} />,
  };

  return (
    <S.VoiceChannelActions>
      {Object.entries(actions).map(([key, value]) => (
        <S.Action key={key}>{value}</S.Action>
      ))}
    </S.VoiceChannelActions>
  );
};

export default VoiceChannelActions;
