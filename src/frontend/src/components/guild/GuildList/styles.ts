import { BiCompass } from 'react-icons/bi';
import { BsDiscord } from 'react-icons/bs';
import { TbPlus } from 'react-icons/tb';
import styled from 'styled-components';

export const GuildList = styled.nav`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 8.4rem;
  height: 100%;
  padding: 2.2rem 1rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};
`;

export const DMButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 6.2rem;
  height: 6.2rem;
  border-radius: 1.4rem;

  background-color: ${({ theme }) => theme.colors.blue};
`;

export const DiscordIcon = styled(BsDiscord)`
  color: ${({ theme }) => theme.colors.white};
`;

export const CircleButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 6.25rem;
  height: 6.2rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const AddGuildButton = styled(CircleButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;
export const SearchCommunityButton = styled(CircleButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;

export const PlusIcon = styled(TbPlus)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;

export const CompassIcon = styled(BiCompass)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;
