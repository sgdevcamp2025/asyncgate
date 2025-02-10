import { TbX } from 'react-icons/tb';
import styled from 'styled-components';

export const Overlay = styled.div`
  position: fixed;
  z-index: 1001;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  background-color: rgb(0 0 0 / 50%);
`;

export const ModalContainer = styled.div`
  position: fixed;
  z-index: 1002;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  min-width: 49rem;
  min-height: 36rem;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.dark[500]};

  svg {
    color: ${({ theme }) => theme.colors.white};
  }
`;

export const HeaderWrapper = styled.div`
  padding: 0 2.4rem;
  text-align: center;
`;

export const ContentWrapper = styled.div`
  margin-top: 0.8rem;
  padding: 0 2.4rem;
`;

export const FooterWrapper = styled.div`
  display: flex;
  gap: 1.2rem;
  align-items: center;
  justify-content: center;

  margin-top: 1.6rem;
`;

export const CloseButton = styled.div`
  display: flex;
  justify-content: end;
  padding: 2.4rem 2.4rem 0;
`;

export const CloseIcon = styled(TbX)`
  cursor: pointer;
`;
