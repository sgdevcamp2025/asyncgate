import type { DefaultTheme } from 'styled-components';

const colors = {
  black: '#1B1D1F',
  white: '#FFFFFF',
  dark: {
    800: '#1E1F22',
    700: '#2B2D31',
    600: '#2E3035',
    500: '#383A40',
    400: '#80848E',
  },
  red: '#FF595E',
  green: '#248045',
  blue: '#5765F2',
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
