import { Outlet } from 'react-router-dom';

import * as S from './styles';

const FullLayout = () => {
  return (
    <S.LayoutContainer>
      <Outlet />
    </S.LayoutContainer>
  );
};

export default FullLayout;
