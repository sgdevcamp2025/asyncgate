import styled from 'styled-components';

import { TitleText2 } from '@/styles/Typography';

export const FriendTitle = styled.div`
  display: flex;
  align-items: center;

  height: 4.8rem;
  padding: 1.2rem 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.dark[500]};

  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const TitleName = styled(TitleText2)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const TabList = styled.ul`
  display: flex;
  gap: 0.1rem;
  padding: 0.8rem 0.8rem 0;
  list-style: none;
`;

export const TabItem = styled.li<{ $isSelected: boolean }>`
  cursor: pointer;

  display: flex;
  flex-grow: 1;
  align-items: center;

  height: 4.4rem;
  padding: 0 0.8rem;
  border-radius: 0.4rem;

  background-color: ${({ theme, $isSelected }) => ($isSelected ? theme.colors.dark[500] : '')};

  & > * {
    color: ${({ theme, $isSelected }) => ($isSelected ? theme.colors.white : theme.colors.dark[300])};
  }

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};

    & > * {
      color: ${({ theme }) => theme.colors.white};
    }
  }
`;

export const TabIcon = styled.div`
  margin-right: 1.2rem;
`;
