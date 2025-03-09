import { BiHash } from 'react-icons/bi';
import { BsFillMicFill } from 'react-icons/bs';
import { TbPlus } from 'react-icons/tb';

import { useChannelActionStore } from '@/stores/channelAction';
import { GuildChannelInfo, useChannelInfoStore } from '@/stores/channelInfo';
import { useGuildInfoStore } from '@/stores/guildInfo';
import useModalStore from '@/stores/modalStore';
import { BodyRegularText, ChipText } from '@/styles/Typography';
import { CategoryDataResult, ChannelResult, ChannelType } from '@/types/guilds';

import CreateChannelModal from '../CreateChannelModal';

import * as S from './styles';

export interface CategoriesListProps {
  categories?: CategoryDataResult[];
  channels?: ChannelResult[];
}
const GuildCategoriesList = ({ categories, channels }: CategoriesListProps) => {
  const { openModal } = useModalStore();
  const { guildId } = useGuildInfoStore();
  const { selectedChannel, setSelectedChannel } = useChannelInfoStore();
  const { isInVoiceChannel, setIsInVoiceChannel } = useChannelActionStore();

  const handleOpenModal = (categoryId: string, guildId: string) => {
    openModal('withFooter', <CreateChannelModal categoryId={categoryId} guildId={guildId} />);
  };

  const handleChannelClick = (channelInfo: GuildChannelInfo) => {
    setSelectedChannel({ id: channelInfo.id, name: channelInfo.name, type: channelInfo.type });

    if (channelInfo.type === 'VOICE') {
      if (!isInVoiceChannel) setIsInVoiceChannel();
    } else if (isInVoiceChannel) {
      setIsInVoiceChannel();
    }
  };

  return (
    <S.CategoriesList>
      {categories?.map((category) => (
        <S.Category key={category.categoryId}>
          <S.CategoryName>
            <ChipText>{category.name}</ChipText>
            <TbPlus size={18} onClick={() => handleOpenModal(category.categoryId, guildId)} />
          </S.CategoryName>
          <S.Channels>
            {channels
              ?.filter((channel) => category.categoryId === channel.categoryId)
              .map((channel) => (
                <S.ChannelName
                  key={channel.channelId}
                  onClick={() =>
                    handleChannelClick({
                      id: channel.channelId,
                      name: channel.name,
                      type: channel.channelType as ChannelType,
                    })
                  }
                  $isSelected={selectedChannel?.id === channel.channelId}
                >
                  {channel.channelType === 'TEXT' ? <BiHash size={18} /> : <BsFillMicFill size={18} />}
                  <BodyRegularText>{channel.name}</BodyRegularText>
                </S.ChannelName>
              ))}
          </S.Channels>
        </S.Category>
      ))}
    </S.CategoriesList>
  );
};

export default GuildCategoriesList;
