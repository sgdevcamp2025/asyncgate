import styled from 'styled-components';

import { BodyMediumText, BodyRegularText, ChipText } from '@/styles/Typography';

export const ModalTitle = styled(BodyMediumText)`
  font-size: 2rem;
`;

export const SelectedFriendsCount = styled(ChipText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FriendList = styled.ul`
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 0.1rem;

  height: fit-content;
  max-height: 27rem;

  &::-webkit-scrollbar-thumb {
    border: 0.1rem solid ${({ theme }) => theme.colors.dark[600]};
    border-radius: 0.3rem;
    background: ${({ theme }) => theme.colors.dark[800]};
  }

  &::-webkit-scrollbar {
    width: 0.6rem;
  }
`;

export const FriendItem = styled.li`
  cursor: pointer;

  display: flex;
  align-items: center;

  min-height: 4rem;
  padding: 0 0.8rem;
  border-radius: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[600]};
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
  flex-grow: 1;
  gap: 0.4rem;
  align-items: center;

  min-width: 0;
`;

export const FriendNickname = styled(BodyRegularText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const FriendName = styled(ChipText)`
  line-height: 1.4rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const CreateButton = styled.button<{ $isDisabled: boolean }>`
  cursor: ${({ $isDisabled }) => ($isDisabled ? 'not-allowed' : 'pointer')};

  width: 100%;
  height: 3.8rem;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  opacity: ${({ $isDisabled }) => ($isDisabled ? 0.5 : 1)};
  background-color: ${({ theme }) => theme.colors.blue};
`;
