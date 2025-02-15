import { Fragment, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { useLocation } from 'react-router-dom';

import { useModalStore } from '../../../stores/modalStore';

const ModalRenderer = () => {
  const location = useLocation();
  const { modal, closeAllModal } = useModalStore();

  useEffect(() => {
    closeAllModal();
  }, [location.pathname]);

  const portalRoot = document.getElementById('portal-root');
  if (!portalRoot) return null;

  const renderModals = () => {
    return Object.entries(modal).flatMap(([type, typeModals]) => {
      if (!typeModals) return null;
      return <Fragment key={type}>{typeModals.content}</Fragment>;
    });
  };

  return createPortal(<>{renderModals()}</>, portalRoot);
};

export default ModalRenderer;
