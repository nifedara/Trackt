import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";
import { ChakraProvider } from "@chakra-ui/react";
import { appTheme } from "./theme";
import AppContextProvider from "./context";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <AppContextProvider>
      <ChakraProvider theme={appTheme}>
        <App />
      </ChakraProvider>
    </AppContextProvider>
  </React.StrictMode>
);
