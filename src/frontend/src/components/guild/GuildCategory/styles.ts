import styled from 'styled-components';

import { TitleText2, BodyRegularText } from '@/styles/Typography';

export const GuildCategories = styled.div`
  width: 24rem;
  padding: 1.6rem 1rem;
  background-color: ${({ theme }) => theme.colors.dark[700]};
`;

export const GuildTitle = styled.div`
  cursor: pointer;

  position: relative;

  display: flex;
  justify-content: space-between;

  padding-bottom: 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.dark[500]};

  svg {
    cursor: pointer;
    color: ${({ theme }) => theme.colors.dark[300]};
  }
`;

export const GuildName = styled(TitleText2)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const DropDown = styled.div`
  position: absolute;
  z-index: 10;
  top: 6%;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 100%;
  max-width: 24rem;
  padding: 0.8rem 1rem;
  border-radius: 0.4rem;

  background-color: ${({ theme }) => theme.colors.black};
`;

export const DropDownItem = styled.div`
  cursor: pointer;
`;

export const DropDownItemText = styled(BodyRegularText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;
