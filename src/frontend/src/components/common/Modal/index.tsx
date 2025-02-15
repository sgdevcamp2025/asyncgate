import React from 'react';

import useModalStore from '../../../stores/modalStore';
import { ModalType } from '../../../types';

import * as S from './styles';

interface ModalProps {
  children: React.ReactNode;
  name: ModalType;
}

const Modal = ({ children, name }: ModalProps) => {
  const { closeModal, closeAllModal } = useModalStore();

  return (
    <>
      <S.Overlay onClick={() => closeModal(name, 'close-modal')} />
      <S.ModalContainer>
        <S.CloseButton>
          <S.CloseIcon size={28} onClick={() => closeAllModal()} />
        </S.CloseButton>
        {children}
      </S.ModalContainer>
    </>
  );
};

const Header = ({ children }: { children: React.ReactNode }) => {
  return <S.HeaderWrapper>{children}</S.HeaderWrapper>;
};

const Content = ({ children }: { children: React.ReactNode }) => {
  return <S.ContentWrapper>{children}</S.ContentWrapper>;
};

const Footer = ({ children }: { children: React.ReactNode }) => {
  return <S.FooterWrapper>{children}</S.FooterWrapper>;
};

Modal.Header = Header;
Modal.Content = Content;
Modal.Footer = Footer;

export default Modal;
