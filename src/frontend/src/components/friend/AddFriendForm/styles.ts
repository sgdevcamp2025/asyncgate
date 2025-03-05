import styled from 'styled-components';

import { BodyRegularText, TitleText2 } from '@/styles/Typography';

import { ResultMessageType } from '.';

export const AddFriendFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 2rem 3rem;
`;

export const FormTitle = styled(TitleText2)`
  color: ${({ theme }) => theme.colors.white};
`;

export const FormSubtitle = styled(BodyRegularText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FriendSearchInputWrapper = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 5rem;
  margin-top: 1.6rem;
  padding: 0 1.2rem;
  border-radius: 0.8rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};
`;

export const FriendSearchInput = styled.input`
  display: flex;
  flex-grow: 1;
  align-items: center;

  height: 4rem;
  border: none;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.dark[800]};
  outline: none;
`;

export const SendRequestButton = styled.button<{ $isDisabled: boolean }>`
  cursor: ${({ $isDisabled }) => ($isDisabled ? 'not-allowed' : 'pointer')};

  height: 3.2rem;
  padding: 0.2rem 1.6rem;
  border-radius: 0.4rem;

  font-weight: 600;
  color: ${({ theme }) => theme.colors.dark[300]};

  background-color: ${({ theme }) => theme.colors.blue};
`;

export const ResultMessage = styled(BodyRegularText)<{ $type: ResultMessageType }>`
  color: ${({ theme, $type }) => ($type === 'success' ? theme.colors.green : theme.colors.red)};
`;
