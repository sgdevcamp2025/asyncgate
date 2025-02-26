import styled from 'styled-components';

import { ChipText } from '@/styles/Typography';

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  height: 100%;
`;

export const AuthCodeInput = styled.input`
  width: auto;
  height: 4rem;
  padding: 0 2rem;
  border: none;
  border-radius: 0.4rem;

  font-size: 2rem;
  color: ${({ theme }) => theme.colors.white};
  text-align: center;
  letter-spacing: 1rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};
  outline: none;
`;

export const SubmitButton = styled.button`
  width: 100%;
  height: 4rem;
  margin-top: 1rem;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.blue};
`;

export const ErrorMessage = styled(ChipText)`
  margin-top: 0.4rem;
  color: ${({ theme }) => theme.colors.red};
`;
