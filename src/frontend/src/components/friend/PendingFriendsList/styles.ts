import styled from 'styled-components';

import { BodyMediumText, ChipText } from '@/styles/Typography';

export const PendingFriendsListContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

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
  justify-content: space-between;

  height: 6.2rem;
  border-top: 0.1rem solid ${({ theme }) => theme.colors.dark[500]};
  border-radius: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};
  }
`;

export const FriendInfoContainer = styled.div`
  display: flex;
  align-items: center;
`;

export const FriendProfileImage = styled.div<{ $imageUrl: string }>`
  position: relative;

  width: 3.2rem;
  height: 3.2rem;
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

  background-color: ${({ theme, $isOnline }) => ($isOnline ? theme.colors.online : theme.colors.black)};
`;

export const FriendNickname = styled(BodyMediumText)`
  overflow: hidden;

  padding-left: 0.8rem;

  color: ${({ theme }) => theme.colors.white};
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const FriendStatus = styled(ChipText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const AcceptButton = styled.button<{ $isPending: boolean }>`
  pointer-events: ${({ $isPending }) => ($isPending ? 'none' : 'auto')};
  cursor: ${({ $isPending }) => ($isPending ? 'not-allowed' : 'pointer')};

  width: 8rem;
  height: 3.2rem;
  border-radius: 0.4rem;

  font-weight: 500;
  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.green};
`;

export const RejectButton = styled.button<{ $isPending: boolean }>`
  pointer-events: ${({ $isPending }) => ($isPending ? 'none' : 'auto')};
  cursor: ${({ $isPending }) => ($isPending ? 'not-allowed' : 'pointer')};

  width: 8rem;
  height: 3.2rem;
  border-radius: 0.4rem;

  font-weight: 500;
  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.red};
`;

export const Divider = styled.hr`
  margin: 0 2rem 0 3rem;
  border: 0;
  border-top: 0.1rem solid ${({ theme }) => theme.colors.dark[500]};
`;

export const ErrorMessage = styled.div`
  display: flex;
  justify-content: flex-end;
  color: ${({ theme }) => theme.colors.red};
`;
