import { motion } from 'framer-motion';
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

  const bounceAnimation = {
    y: [0, -5, 0],
    transition: {
      duration: 0.6,
      repeat: 3,
      repeatType: 'reverse' as const,
      ease: 'easeInOut',
    },
  };

  return (
    <S.VoiceChannelActions>
      {Object.entries(actions).map(([key, value]) => (
        <motion.div key={key} initial={{ y: 0 }} whileHover={bounceAnimation}>
          <S.Action key={key}>{value}</S.Action>
        </motion.div>
      ))}
    </S.VoiceChannelActions>
  );
};

export default VoiceChannelActions;
