import type { DefaultTheme } from 'styled-components';

const colors = {
  black: '#1B1D1F',
  white: '#FFFFFF',
  dark: {
    800: '#1E1F22',
    750: '#232428',
    700: '#2B2D31',
    600: '#2E3035',
    500: '#383A40',
    450: '#484A51',
    400: '#80848E',
    350: '#9DA2AE',
    300: '#B5BAC1',
  },
  red: '#FF595E',
  green: '#248045',
  online: '#23A55A',
  lightGreen: '#28B964',
  blue: '#5765F2',
  link: '#069BE3',
};

const typography = {
  display: {
    size: '3.2rem',
    lineHeight: 1.4,
    weight: 700,
  },
  header: {
    size: '2.4rem',
    lineHeight: 1.4,
    weight: 600,
  },
  title1: {
    size: '2rem',
    lineHeight: 1.4,
    weight: 600,
  },
  title2: {
    size: '1.8rem',
    lineHeight: 1.4,
    weight: 500,
  },
  bodyR: {
    size: '1.6rem',
    lineHeight: 1.6,
    weight: 400,
  },
  bodyM: {
    size: '1.6rem',
    lineHeight: 1.4,
    weight: 500,
  },
  mediumButton: {
    size: '1.6rem',
    lineHeight: 1.4,
    weight: 600,
  },
  smallButton: {
    size: '1.4rem',
    lineHeight: 1.4,
    weight: 600,
  },
  caption: {
    size: '1.4rem',
    lineHeight: 1.6,
    weight: 400,
  },
  chip: {
    size: '1.4rem',
    lineHeight: 1.4,
    weight: 500,
  },
  smallText: {
    size: '1.2rem',
    lineHeight: 1.4,
    weight: 400,
  },
};

const maxWidth = '128rem';

export type ColorsTypes = typeof colors;
export type TypographyTypes = typeof typography;

const theme: DefaultTheme = {
  colors,
  typography,
  maxWidth,
};

export default theme;
