import styled from 'styled-components';

import { CaptionText } from '@/styles/Typography';

export const ChannelType = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
`;

export const ChannelTypeContent = styled.div<{ $isSelectedType: boolean }>`
  display: flex;
  gap: 1rem;
  align-items: center;

  min-height: 5rem;
  padding: 0.6rem;
  border-radius: 0.8rem;

  background-color: ${({ theme, $isSelectedType }) =>
    $isSelectedType ? theme.colors.dark[450] : theme.colors.dark[600]};

  svg {
    color: ${({ theme }) => theme.colors.dark[400]};
  }

  span {
    color: ${({ theme }) => theme.colors.dark[400]};
  }

  &:hover {
    cursor: pointer;
    background-color: ${({ theme }) => theme.colors.dark[450]};
  }
`;

export const TypeInfo = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ChannelInfoText = styled(CaptionText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const FooterContainer = styled.div`
  display: flex;
  gap: 4rem;
  justify-content: flex-end;

  padding: 1.5rem 2rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const ChannelNameInput = styled.input`
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
