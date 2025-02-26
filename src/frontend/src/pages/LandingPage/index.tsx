import { useNavigate } from 'react-router-dom';

import DownloadIcon from '@/assets/download.svg';

import NavigationBar from './components/NavigationBar';
import * as S from './styles';

const LandingPage = () => {
  const navigate = useNavigate();

  const handleOpenButtonClick = () => {
    navigate('/login');
  };
  return (
    <S.PageContainer>
      <NavigationBar />
      <S.DescriptionContainer>
        <S.IntroContainer>
          <S.Title>재미와 게임으로 가득한 그룹 채팅</S.Title>
          <S.Description>
            AsyncGate는 친구들과 게임을 플레이하며 놀거나 글로벌 커뮤니티를 만들기에 좋습니다. 나만의 공간을 만들어
            대화하고, 게임을 플레이하며, 어울려 보세요.
          </S.Description>
        </S.IntroContainer>
        <S.PreviewImageWrapper />
      </S.DescriptionContainer>
      <S.ButtonContainer>
        <S.DownloadButton>
          <img src={DownloadIcon} alt="" />
          {'Windows용 다운로드'}
        </S.DownloadButton>
        <S.OpenButton onClick={handleOpenButtonClick}>웹브라우저에서 AsyncGate 열기</S.OpenButton>
      </S.ButtonContainer>
    </S.PageContainer>
  );
};

export default LandingPage;
