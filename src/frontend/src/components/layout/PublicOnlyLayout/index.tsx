import { Outlet } from 'react-router-dom';

import LogoIcon from '@/assets/logo.svg';

import * as S from './styles';

const PublicOnlyLayout = () => {
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
