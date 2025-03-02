import { useState } from 'react';

import { getFriends, postFriendRequest } from '@/api/friends';

import useGetFriendsList from '../FriendsList/hooks/useGetFriendsList';
import useGetReceivedRequests from '../PendingFriendsList/hooks/useGetReceivedRequests';
import useGetSentRequests from '../PendingFriendsList/hooks/useGetSentRequests';

import * as S from './styles';

export type ResultMessageType = 'success' | 'fail';

interface ResultMessage {
  type: ResultMessageType;
  content: string;
}

const AddFriendForm = () => {
  const [inputData, setInputData] = useState('');
  const [resultMessage, setResultMessage] = useState<ResultMessage | null>(null);

  const { friends } = useGetFriendsList();
  const { sentRequests } = useGetSentRequests();
  const { receivedRequests } = useGetReceivedRequests();

  const handleInputChange = (value: string) => {
    setInputData(value);
  };

  const getFriendInfoByEmail = async () => {
    // 1. 이미 친구로 등록된 사용자인지 확인
    if (friends && friends.some((friend) => friend.email === inputData)) {
      setResultMessage({ type: 'fail', content: '이미 친구로 등록된 사용자예요.' });
      return null;
    }

    // 2. 이미 친구 요청을 보낸 사용자인지 확인
    if (sentRequests && sentRequests.some((request) => request.email === inputData)) {
      setResultMessage({ type: 'fail', content: '이미 친구 요청을 보냈어요. 상대방의 응답을 기다려주세요.' });
      return null;
    }

    // 3. 상대가 이미 친구 요청을 보냈는지 확인
    if (receivedRequests && receivedRequests.some((request) => request.email === inputData)) {
      setResultMessage({ type: 'fail', content: '상대방이 이미 친구 요청을 보냈어요. 친구 요청을 확인해주세요.' });
      return null;
    }

    try {
      const response = await getFriends({ email: inputData });
      return response;
    } catch (error) {
      console.log('회원 검색 실패', error);
      setResultMessage({ type: 'fail', content: '회원 검색에 실패했어요. 다시 시도해주세요.' });
      return null;
    }
  };

  const handleSendRequestButtonClick = async () => {
    try {
      const friendInfo = await getFriendInfoByEmail();
      if (!friendInfo) return;

      await postFriendRequest({ toUserId: friendInfo.userId }); // friendInfo에 userId가 있어야 함

      setResultMessage({ type: 'success', content: '친구 요청을 보냈어요!' });
    } catch (error) {
      console.log('친구 요청 실패', error);
      setResultMessage({ type: 'fail', content: '친구 요청을 보내는 데 실패했어요. 다시 시도해주세요.' });
    }
  };

  return (
    <S.AddFriendFormContainer>
      <S.FormTitle>친구 추가하기</S.FormTitle>
      <S.FormSubtitle>AsyncGate 이메일을 사용하여 친구를 추가할 수 있어요</S.FormSubtitle>
      <S.FriendSearchInputWrapper>
        <S.FriendSearchInput
          onChange={(e) => handleInputChange(e.target.value)}
          placeholder="AsyncGate 이메일을 사용하여 친구를 추가할 수 있어요."
        />
        <S.SendRequestButton $isDisabled={!inputData.trim()} onClick={handleSendRequestButtonClick}>
          친구 요청 보내기
        </S.SendRequestButton>
      </S.FriendSearchInputWrapper>
      {resultMessage && <S.ResultMessage $type={resultMessage.type}>{resultMessage.content}</S.ResultMessage>}
    </S.AddFriendFormContainer>
  );
};

export default AddFriendForm;
