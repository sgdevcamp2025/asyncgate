import styled from 'styled-components';

import { ChipText } from '@/styles/Typography';

export const UserProfileContainer = styled.div`
  position: relative;

  display: flex;
  align-items: center;

  width: 100%;
  height: 5.2rem;
  padding: 0 1rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};
`;

export const UserInfoContainer = styled.div`
  cursor: pointer;

  display: flex;
  align-items: center;

  width: 12rem;
  border-radius: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};
  }
`;

export const UserImage = styled.div<{ $userImageUrl: string }>`
  position: relative;

  min-width: 3.2rem;
  height: 3.2rem;
  border-radius: 50%;

  background-color: ${({ theme }) => theme.colors.white};
  background-image: url(${(props) => props.$userImageUrl});
`;

export const UserStatusMark = styled.div<{ $isOnline: boolean }>`
  position: absolute;
  right: 0;
  bottom: 0;

  width: 1rem;
  height: 1rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 50%;

  background-color: ${({ $isOnline }) => ($isOnline ? 'green' : 'black')};
`;

export const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0.4rem 0 0.4rem 0.8rem;
`;

export const UserName = styled(ChipText)`
  width: 100%;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.colors.white};
`;

export const UserStatus = styled(ChipText)`
  width: 100%;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const ControlButtonContainer = styled.div`
  display: flex;
  align-items: center;
`;

export const ControlButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 3.2rem;
  height: 3.2rem;
  border-radius: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};
  }
`;
