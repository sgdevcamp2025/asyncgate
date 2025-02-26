import { BiCompass } from 'react-icons/bi';
import { BsDiscord } from 'react-icons/bs';
import { TbPlus } from 'react-icons/tb';
import styled from 'styled-components';

export const GuildList = styled.nav`
  scrollbar-width: none;

  overflow-x: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 8.4rem;
  height: 100vh;
  padding: 2.2rem 1rem;

  background-color: ${({ theme }) => theme.colors.dark[800]};

  -ms-overflow-style: none;

  &::-webkit-scrollbar {
    display: none;
    width: 0;
  }
`;

export const DMButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 6.2rem;
  min-height: 6.2rem;
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

  min-width: 6.25rem;
  min-height: 6.2rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const AddGuildButton = styled(CircleButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;
export const SearchCommunityButton = styled(CircleButton)`
  /* 버튼별 역할 구별을 위해 분리 */
`;

export const GuildButton = styled.button<{ $imageUrl: string }>`
  position: relative;

  min-width: 6.25rem;
  min-height: 6.2rem;
  margin-bottom: 8px;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.dark[600]};
  background-image: url(${(props) => props.$imageUrl});
  background-position: center;
  background-size: cover;

  &:hover {
    border-radius: 30%;

    &::after {
      content: attr(data-tooltip);

      position: absolute;
      z-index: 10;
      top: 50%;
      left: 60px;
      transform: translateY(-50%);

      padding: 6px 10px;
      border-radius: 4px;

      font-size: 14px;
      color: #fff;
      white-space: nowrap;

      background-color: #000;
    }

    &::before {
      content: '';

      position: absolute;
      z-index: 10;
      top: 50%;
      left: 56px;
      transform: translateY(-50%);

      border-top: 5px solid transparent;
      border-right: 5px solid #000;
      border-bottom: 5px solid transparent;
    }
  }
`;

export const PlusIcon = styled(TbPlus)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;

export const CompassIcon = styled(BiCompass)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;
