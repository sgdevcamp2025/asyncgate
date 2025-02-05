import { Outlet } from 'react-router-dom';

import * as S from './styles';

const PublicOnlyLayout = () => {
  return (
    <S.LayoutContainer>
      <Outlet />
    </S.LayoutContainer>
  );
};

export default PublicOnlyLayout;
