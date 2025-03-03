import styled from 'styled-components';

import { BodyMediumText } from '@/styles/Typography';

export const ConfirmText = styled(BodyMediumText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const ModalButtonContainer = styled.div`
  display: flex;
  gap: 1.6rem;
  align-items: center;
  justify-content: flex-end;

  height: 7rem;
  padding: 1.6rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const CancelButton = styled.button`
  height: 3.8rem;
  padding: 0.2rem 1.6rem;
  color: ${({ theme }) => theme.colors.white};

  &:hover {
    text-decoration: underline;
  }
`;

export const DeleteButton = styled.button`
  height: 3.8rem;
  padding: 0.2rem 1.6rem;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.red};

  &:hover {
    opacity: 0.8;
  }
`;
