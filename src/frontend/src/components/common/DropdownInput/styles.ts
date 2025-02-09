import styled from 'styled-components';

export const DropdownContainer = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  min-height: 4rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[600]};
  border-radius: 0.4rem;

  word-break: keep-all;

  background-color: ${({ theme }) => theme.colors.dark[800]};
`;

export const DropdownBody = styled.div`
  position: relative;
  display: flex;
  width: 100%;
`;

export const SelectedItem = styled.div`
  position: absolute;
  top: 50%;
  left: 0;
  transform: translate(0, -50%);

  max-width: calc(100% - 0.8rem);
  padding: 0.2rem 0.8rem;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const DropdownInput = styled.input`
  cursor: default;

  display: flex;

  width: 100%;
  padding: 0.2rem 0.8rem;
  border: none;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};

  background-color: ${({ theme }) => theme.colors.dark[800]};
  outline: none;
  caret-color: ${({ theme }) => theme.colors.white};
`;

export const ArrowIcon = styled.img`
  width: 2rem;
  height: 2rem;
  margin: 0.8rem 0.8rem 0.8rem 0;
`;

export const ItemContainer = styled.ul`
  position: absolute;
  bottom: 4rem;

  overflow-y: scroll;
  display: flex;
  flex-direction: column;

  width: 100%;
  max-height: 22rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[800]};
  border-radius: 0.3rem;

  background-color: ${({ theme }) => theme.colors.dark[700]};

  &::-webkit-scrollbar-thumb {
    border: 0.1rem solid ${({ theme }) => theme.colors.dark[600]};
    border-radius: 0.3rem;
    background: ${({ theme }) => theme.colors.dark[800]};
  }

  &::-webkit-scrollbar {
    width: 0.6rem;
  }
`;

export const EmptyItem = styled.li`
  display: flex;
  align-items: center;

  min-height: 4rem;
  padding: 0.2rem 1rem;

  font-size: 1.6rem;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const DropdownItem = styled.li<{ $isSelected: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;

  min-height: 4rem;
  padding: 0.2rem 1rem;

  font-size: 1.6rem;
  font-weight: 500;
  color: ${({ $isSelected, theme }) => ($isSelected ? theme.colors.white : theme.colors.dark[300])};

  background-color: ${({ $isSelected, theme }) => ($isSelected ? theme.colors.dark[500] : 'inherit')};

  &:hover {
    color: ${({ $isSelected, theme }) => !$isSelected && theme.colors.dark[300]};
    background-color: ${({ $isSelected, theme }) => !$isSelected && theme.colors.dark[600]};
  }
`;
