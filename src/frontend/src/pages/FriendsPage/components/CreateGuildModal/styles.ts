import styled from 'styled-components';

import { BodyMediumText } from '../../../../styles/Typography';

export const CreateGuildModal = styled.div`
  display: flex;
`;

export const CreateButtons = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  padding: 3.6rem 4.6rem 0;
`;

const BaseGuildButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0.9rem 2rem;
  border: 1px solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 0.8rem;

  color: ${({ theme }) => theme.colors.white};
`;

export const CreatePrivateGuild = styled(BaseGuildButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;

export const CreatePublicGuild = styled(BaseGuildButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;

export const HeaderText = styled(BodyMediumText)`
  font-size: 1.8rem;
`;
