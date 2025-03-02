import styled from 'styled-components';

import { BodyMediumText, ChipText } from '@/styles/Typography';

export const FriendsList = styled.ul`
  display: flex;
  flex-direction: column;

  width: 100%;
  margin-top: 0.8rem;
  padding: 0 2rem 0 3rem;

  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FriendCount = styled(ChipText)`
  margin: 1.6rem 0 1rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FriendItem = styled.li`
  cursor: pointer;

  display: flex;
  align-items: center;

  height: 6.2rem;
  border-top: 0.1rem solid ${({ theme }) => theme.colors.dark[500]};
  border-radius: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};
  }
`;

export const FriendProfileImage = styled.div<{ $imageUrl: string }>`
  position: relative;

  width: 3.2rem;
  height: 3.2rem;
  margin-right: 1.2rem;
  border-radius: 50%;

  background-color: ${({ theme }) => theme.colors.white};
  background-image: url(${(props) => props.$imageUrl});
`;

export const FriendStatusMark = styled.div<{ $isOnline: boolean }>`
  position: absolute;
  right: 0;
  bottom: 0;

  width: 1rem;
  height: 1rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 50%;

  background-color: ${({ $isOnline }) => ($isOnline ? 'green' : 'black')};
`;

export const FriendInfo = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  min-width: 0;
`;

export const FriendName = styled(BodyMediumText)`
  overflow: hidden;
  color: ${({ theme }) => theme.colors.white};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const FriendStatus = styled(ChipText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;
