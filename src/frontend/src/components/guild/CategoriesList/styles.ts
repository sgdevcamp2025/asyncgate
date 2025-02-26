import styled from 'styled-components';

export const CategoriesList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  margin-top: 1.5rem;

  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const Category = styled.div`
  display: flex;
  flex-direction: column;
`;

export const CategoryName = styled.div`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;

  svg {
    color: ${({ theme }) => theme.colors.dark[300]};
  }
`;

export const Channels = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  margin-top: 0.6rem;
`;

export const ChannelName = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  padding-left: 1rem;
`;
