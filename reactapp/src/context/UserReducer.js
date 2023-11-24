const authReducer = (state, action) => {
  const { type, payload } = action;
  switch (type) {
    case "set_Token":
      return { ...state, token: payload.data };
    case "set_Page_IsLoaded":
      return {
        ...state,
        pageIsLoaded: payload,
      };
    case "LOGOUT":
      return { ...state, token: "" };
    case "init_stored":
      return payload.auth;
    case "set_user":
      return payload.data;
    default:
      return state;
  }
};

export default authReducer;
