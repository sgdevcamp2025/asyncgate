import styled from 'styled-components';

export const LayoutContainer = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

export const Background = styled.div`
  position: fixed;
  z-index: 0;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  background-image: url('/src/assets/background.svg');
  background-repeat: no-repeat;
  background-size: cover;
`;

export const Logo = styled.div`
  position: absolute;
  top: 4.8rem;
  left: 4.8rem;
`;
