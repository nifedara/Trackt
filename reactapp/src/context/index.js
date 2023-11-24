import { createContext, useEffect, useReducer } from "react";
import authReducer from "./UserReducer";
import { decodeToken } from "react-jwt";
import { isExpired } from "react-jwt";

const initialState = {
  auth: {
    token: null,
    userData: null,
  },
};

//Create your global context
const AppContext = createContext(initialState);

//Create combined reducers
const combinedReducers = (state, action) => ({
  auth: authReducer(state.auth, action),
});

const APP_NAME = "Trackt";

const AppContextProvider = ({ children }) => {
  //Making it to provider state
  const [state, dispatch] = useReducer(combinedReducers, initialState);

  const checkCookieForAuthToken = () => {
    if (state.auth.token) {
      const isExpired = isExpired(state.auth.token);

      if (!isExpired) {
        dispatch({
          type: "set_Token",
          payload: "Bearer 1|hsHMqVStqDNjyzuXIPNf5qmPvOKrJWH3CJbGiEuY",
        });
        dispatch({
          type: "set_Page_IsLoaded",
          payload: true,
        });
      } else {
        dispatch({
          type: "set_Page_IsLoaded",
          payload: true,
        });
      }
    } else {
      dispatch({
        type: "set_Page_IsLoaded",
        payload: true,
      });
    }
  };

  useEffect(() => {
    const value = localStorage.getItem(APP_NAME);

    if (typeof value === "string") {
      const existingData = JSON.parse(value); // ok

      if (existingData) {
        //checking if there already is a state in localstorage
        //if yes, update the current state with the stored one
        dispatch({
          type: "init_stored",
          payload: existingData,
        });
      }
    }
    checkCookieForAuthToken();
  }, []);

  useEffect(() => {
    localStorage.setItem(APP_NAME, JSON.stringify(state));
  }, [state]);

  //@ts-ignore
  return <AppContext.Provider value={{ state, dispatch }}>{children}</AppContext.Provider>;
};

export default AppContextProvider;

export { AppContext, AppContextProvider };
