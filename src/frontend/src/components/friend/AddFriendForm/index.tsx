import { useState } from 'react';

import { getFriendInfo, getFriendsList, getReceivedRequest, getSentRequest, postFriendRequest } from '@/api/friends';
import useGetUserInfo from '@/pages/FriendsPage/components/UserProfile/hooks/useGetUserInfo';

import * as S from './styles';

export type ResultMessageType = 'success' | 'fail';

interface ResultMessage {
  type: ResultMessageType;
  content: string;
}

const AddFriendForm = () => {
  const { userInfo } = useGetUserInfo();
  const [inputData, setInputData] = useState('');
  const [resultMessage, setResultMessage] = useState<ResultMessage | null>(null);

  const handleInputChange = (value: string) => {
    setInputData(value);
  };

  const getFriendInfoByEmail = async () => {
    try {
      // 버튼을 눌렀을 때 요청
      const [friends, sentRequests, receivedRequests] = await Promise.all([
        getFriendsList(),
        getSentRequest(),
        getReceivedRequest(),
      ]);

      // 1. 자신을 친구로 추가하려 하는지 확인
      if (userInfo?.email === inputData) {
        setResultMessage({ type: 'fail', content: '본인은 추가할 수 없어요.' });
        return null;
      }

      // 2. 이미 친구로 등록된 사용자인지 확인
      if (friends && friends.some((friend) => friend.email === inputData)) {
        setResultMessage({ type: 'fail', content: '이미 친구로 등록된 사용자예요.' });
        return null;
      }

      // 3. 이미 친구 요청을 보낸 사용자인지 확인
      if (sentRequests && sentRequests.some((request) => request.email === inputData)) {
        setResultMessage({ type: 'fail', content: '이미 친구 요청을 보냈어요. 상대방의 응답을 기다려주세요.' });
        return null;
      }

      // 4. 상대가 이미 친구 요청을 보냈는지 확인
      if (receivedRequests && receivedRequests.some((request) => request.email === inputData)) {
        setResultMessage({ type: 'fail', content: '상대방이 이미 친구 요청을 보냈어요. 친구 요청을 확인해주세요.' });
        return null;
      }

      const response = await getFriendInfo({ email: inputData });
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

      await postFriendRequest({ toUserId: friendInfo.userId });

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
