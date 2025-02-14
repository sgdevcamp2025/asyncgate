import { TbPlus } from 'react-icons/tb';
import styled from 'styled-components';

export const AddGuildButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 6.25rem;
  height: 6.2rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const PlusIcon = styled(TbPlus)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;
