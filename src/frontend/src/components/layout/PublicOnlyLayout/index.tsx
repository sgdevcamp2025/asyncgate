import { useEffect } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';

import LogoIcon from '@/assets/logo.svg';

import * as S from './styles';

const PublicOnlyLayout = () => {
  const navigate = useNavigate();
  useEffect(() => {
    // 로그인 한 사용자가 접근하는 경우 리다이렉트
    if (localStorage.getItem('access_token')) navigate('/friends', { replace: true });
  }, []);

  return (
    <S.LayoutContainer>
      <S.Background />
      <S.Logo>
        <img src={LogoIcon} />
      </S.Logo>
      <Outlet />
    </S.LayoutContainer>
  );
};

export default PublicOnlyLayout;
