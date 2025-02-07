import styled from 'styled-components';

import { SmallText } from '@/styles/Typography';

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const InputLabel = styled(SmallText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const RequiredMark = styled.span`
  width: 1.2rem;
  height: 1.2rem;
  color: ${({ theme }) => theme.colors.red};
`;

export const Input = styled.input`
  width: 100%;
  height: 4rem;
  padding: 1rem;
  border: none;
  border-radius: 0.3rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.dark[800]};
  outline: none;
  caret-color: ${({ theme }) => theme.colors.white};
`;

export const ErrorText = styled(SmallText)`
  color: ${({ theme }) => theme.colors.red};
`;
