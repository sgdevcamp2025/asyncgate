import styled from 'styled-components';

import { ChipText } from '@/styles/Typography';

export const DropdownContainer = styled.ul`
  position: absolute;
  bottom: 6rem;
  left: 0;

  width: 120%;
  padding: 1.6rem;
  border-radius: 0.4rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};
`;

export const DropdownMenu = styled.li`
  cursor: pointer;

  display: flex;
  align-items: center;

  width: 100%;
  padding: 1.2rem 1rem;
  border-radius: 0.4rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};

  &:hover {
    background-color: ${({ theme }) => theme.colors.dark[500]};
  }
`;

export const MenuContent = styled(ChipText)`
  color: ${({ theme }) => theme.colors.white};
`;
