import styled from 'styled-components';

import { BodyMediumText } from '@/styles/Typography';

export const DirectMessagesListContainer = styled.div`
  display: flex;
  flex-direction: column;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const CategoryName = styled.div`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  padding: 1.8rem 1.6rem 0.4rem;

  svg {
    color: ${({ theme }) => theme.colors.dark[300]};
  }

  &:hover {
    * {
      color: ${({ theme }) => theme.colors.white};
    }
  }
`;

export const DirectMessagesList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.1rem;

  width: 100%;
  margin-top: 0.6rem;
  padding: 0 0.8rem;
`;

export const DirectMessageItem = styled.li<{ $isSelected: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;

  width: 100%;
  height: 4.4rem;
  padding: 0 0.8rem;
  border-radius: 0.4rem;

  color: ${({ theme, $isSelected }) => ($isSelected ? theme.colors.white : theme.colors.dark[300])};

  background-color: ${({ theme, $isSelected }) => $isSelected && theme.colors.dark[500]};

  &:hover {
    color: ${({ theme }) => theme.colors.white};
  }
`;

export const DirectMessageImage = styled.div<{ $userImageUrl: string }>`
  position: relative;

  min-width: 3.2rem;
  height: 3.2rem;
  margin-right: 1.2rem;
  border-radius: 50%;

  background-color: ${({ theme }) => theme.colors.white};
  background-image: url(${(props) => props.$userImageUrl});
  background-repeat: no-repeat;
  background-size: cover;
`;

export const UserStatusMark = styled.div<{ $isOnline: boolean }>`
  position: absolute;
  right: 0;
  bottom: 0;

  width: 1rem;
  height: 1rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 50%;

  background-color: ${({ theme, $isOnline }) => ($isOnline ? theme.colors.online : theme.colors.black)};
`;

export const DirectMessageInfo = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  min-width: 0;
`;

export const DirectMessageName = styled(BodyMediumText)`
  overflow: hidden;
  display: block;

  width: 100%;

  text-overflow: ellipsis;
  white-space: nowrap;
`;
