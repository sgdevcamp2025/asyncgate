import styled from 'styled-components';

import { CaptionText } from '@/styles/Typography';

export const CategoryNameInput = styled.input`
  display: flex;
  align-items: center;

  width: 100%;
  height: 4rem;
  margin: 0.4rem 0;
  padding: 0.4rem 0 0.4rem 0.8rem;
  border: none;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.dark[800]};
  outline: none;
`;

export const PrivateSetting = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 1rem 0;
`;

export const SettingText = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;

  svg {
    color: ${({ theme }) => theme.colors.white};
  }
`;

export const CategoryInfoText = styled(CaptionText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FooterContainer = styled.div`
  display: flex;
  gap: 4rem;
  justify-content: flex-end;

  padding: 1.5rem 2rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const CancelButton = styled.button`
  color: ${({ theme }) => theme.colors.white};
  background-color: transparent;
`;

export const CreateButton = styled.button<{ $disabled: boolean }>`
  cursor: ${({ $disabled }) => ($disabled ? 'not-allowed' : 'pointer')};

  width: 11rem;
  height: 4rem;
  border-radius: 0.8rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme, $disabled }) => ($disabled ? theme.colors.dark[400] : theme.colors.blue)};
`;
