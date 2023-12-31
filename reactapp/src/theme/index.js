import { extendTheme, withDefaultColorScheme } from "@chakra-ui/react";

export const appTheme = extendTheme(
  {
    fonts: {
      heading: `Caudex, sans-serif`,
      body: `Caudex, sans-serif`,
    },
    colors: {
      primary: {
        50: "#fde7f5",
        100: "#fab7e2",
        200: "#f787ce",
        300: "#f457bb",
        400: "#f127a7",
        500: "#d80e8e",
        600: "#a80b6e",
        700: "#78084f",
        800: "#48052f",
        900: "#180210",
      },
    },
  },
  withDefaultColorScheme({ colorScheme: "primary" })
);
