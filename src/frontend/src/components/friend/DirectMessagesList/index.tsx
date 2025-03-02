import { useQuery } from '@tanstack/react-query';
import { TbPlus } from 'react-icons/tb';

import { getDirects } from '@/api/directMessages';
import { useChannelInfoStore } from '@/stores/channelInfo';
import { useUserInfoStore } from '@/stores/userInfo';
import { ChipText } from '@/styles/Typography';

import * as S from './styles';

const DirectMessagesList = () => {
  const { userInfo } = useUserInfoStore();
  const { selectedDMChannel, setSelectedDMChannel } = useChannelInfoStore();
  const { data: directMessages } = useQuery({ queryKey: ['directMessagesList'], queryFn: getDirects });
  if (!directMessages) return null;

  return (
    <S.DirectMessagesListContainer>
      <S.CategoryName>
        <ChipText>다이렉트 메시지</ChipText>
        <TbPlus size={18} />
      </S.CategoryName>
      <S.DirectMessagesList>
        {directMessages.map(({ directId, members }) => {
          const directMessageName = members.responses
            .filter((member) => member.userId !== userInfo?.userId)
            .map((member) => member.nickname)
            .join(', ');

          return (
            <S.DirectMessageItem
              key={directId}
              $isSelected={selectedDMChannel?.id === directId}
              onClick={() => setSelectedDMChannel({ id: directId, name: directMessageName, type: 'TEXT' })}
            >
              <S.DirectMessageImage $userImageUrl={members.responses[0].profileImageUrl}>
                {members.responses.length === 1 && <S.UserStatusMark $isOnline={true} />}
              </S.DirectMessageImage>
              <S.DirectMessageInfo>
                <S.DirectMessageName>{directMessageName}</S.DirectMessageName>
                {members.responses.length > 1 && <ChipText>멤버 {members.responses.length}명</ChipText>}
              </S.DirectMessageInfo>
            </S.DirectMessageItem>
          );
        })}
      </S.DirectMessagesList>
    </S.DirectMessagesListContainer>
  );
};

export default DirectMessagesList;
