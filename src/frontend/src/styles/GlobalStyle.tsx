import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  @import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');

  *,
  *::before,
  *::after {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Pretendard', -apple-system, sans-serif;
  }

  html,
  body {
    margin: 0;
    padding: 0;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f9f9f9;
    font-size: 62.5%;
    color: ${({ theme }) => theme.colors.dark[700]};
  }

  button {
    border: none;
    background: none;
    cursor: pointer;
  }

  #root {
    width: 100%;
    height: 100%;
  }
`;

export default GlobalStyle;
