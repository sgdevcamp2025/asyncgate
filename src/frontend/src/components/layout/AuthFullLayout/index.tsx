import { Outlet } from 'react-router-dom';

import * as S from './styles';

const AuthFullLayout = () => {
  return (
    <S.LayoutContainer>
      <Outlet />
    </S.LayoutContainer>
  );
};

export default AuthFullLayout;
