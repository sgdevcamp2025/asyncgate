import 'styled-components';
import type { ColorsTypes, TypographyTypes } from './theme';

declare module 'styled-components' {
  export interface DefaultTheme {
    colors: ColorsTypes;
    typography: TypographyTypes;
    maxWidth: string;
  }
}
