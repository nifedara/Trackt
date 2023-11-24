import { useContext } from "react";
import { AppContext } from "../context";

export const useAuth = () => {
  const {
    state: { auth },
  } = useContext(AppContext);

  return { auth };
};
