import { BiHash } from 'react-icons/bi';
import { BsFillMicFill } from 'react-icons/bs';
import { TbPlus } from 'react-icons/tb';

import { useGuildInfoStore } from '@/stores/guildInfo';
import useModalStore from '@/stores/modalStore';
import { BodyMediumText, BodyRegularText } from '@/styles/Typography';
import { CategoryDataResult, ChannelResult } from '@/types/guilds';

import CreateChannelModal from '../CreateChannelModal';

import * as S from './styles';

export interface CategoriesListProps {
  categories?: CategoryDataResult[];
  channels?: ChannelResult[];
}
const CategoriesList = ({ categories, channels }: CategoriesListProps) => {
  const { openModal } = useModalStore();
  const { guildId } = useGuildInfoStore();
  const handleOpenModal = (categoryId: string, guildId: string) => {
    openModal('withFooter', <CreateChannelModal categoryId={categoryId} guildId={guildId} />);
  };

  return (
    <S.CategoriesList>
      {categories?.map((category) => (
        <S.Category key={category.categoryId}>
          <S.CategoryName>
            <BodyMediumText>{category.name}</BodyMediumText>
            <TbPlus size={18} onClick={() => handleOpenModal(category.categoryId, guildId)} />
          </S.CategoryName>
          <S.Channels>
            {channels
              ?.filter((channel) => category.categoryId === channel.categoryId)
              .map((channel) => (
                <S.ChannelName key={channel.channelId}>
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

export default CategoriesList;
