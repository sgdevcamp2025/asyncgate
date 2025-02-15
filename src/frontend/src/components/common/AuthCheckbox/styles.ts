import styled from 'styled-components';

export const CheckboxContainer = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;
  width: fit-content;
`;

export const Checkbox = styled.div<{ $isChecked: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 2.4rem;
  height: 2.4rem;
  border: 0.1rem solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 0.6rem;

  background-color: ${({ $isChecked, theme }) => ($isChecked ? theme.colors.blue : 'inherit')};

  & > img {
    display: ${({ $isChecked }) => ($isChecked ? 'block' : 'none')};
    width: 2.2rem;
    height: 2.2rem;
  }
`;

export const Label = styled.label<{ $style?: React.CSSProperties }>`
  padding-left: 0.8rem;

  font-size: 1.2rem;
  font-weight: 400;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};

  ${({ $style }) => $style && { ...$style }};
`;
