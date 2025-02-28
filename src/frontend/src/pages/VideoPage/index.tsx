import { useState } from 'react';

import { BodyRegularText, TitleText1 } from '@/styles/Typography';

import * as S from './styles';

const VideoPage = () => {
  const [isAttend, setIsAttend] = useState(false);

  return (
    <S.VideoPage>
      {isAttend ? (
        <>참여시 비디오들</>
      ) : (
        <S.EmptyParticipant>
          <TitleText1>채널 이름</TitleText1>
          <BodyRegularText>현재 음성 채널에 아무도 없어요</BodyRegularText>
        </S.EmptyParticipant>
      )}
    </S.VideoPage>
  );
};

export default VideoPage;
