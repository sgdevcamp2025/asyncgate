import { useNavigate } from 'react-router-dom';

import LogoIcon from '@/assets/logo.svg';

import useNavigationBar from './hooks/useNavigationBar';
import * as S from './styles';

const NavigationBar = () => {
  const navigate = useNavigate();
  const { menuList } = useNavigationBar();

  const handleLoginButtonClick = () => {
    navigate('/login');
  };

  return (
    <S.NavBarContainer>
      <S.Logo>
        <img src={LogoIcon} alt="" />
      </S.Logo>
      <S.MenuContainer>
        {menuList.map((menu) => {
          return (
            <S.MenuItem key={menu.title}>
              <a href={menu.url}>{menu.title}</a>
            </S.MenuItem>
          );
        })}
      </S.MenuContainer>
      <S.LoginButton onClick={handleLoginButtonClick}>로그인</S.LoginButton>
    </S.NavBarContainer>
  );
};

export default NavigationBar;
