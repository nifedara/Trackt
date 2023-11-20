import { createBrowserRouter } from "react-router-dom";
import { LoginPage, LandingPage, SignUpPage } from "./pages";
import { AuthLayout } from "./components/AuthLayout";
import { Destinations } from "./pages/Destinations";

export const Routes = createBrowserRouter([
  {
    path: "/",
    element: <LandingPage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/signup",
    element: <SignUpPage />,
  },
  {
    element: <AuthLayout />,
    children: [
      {
        path: "/destinations",
        element: <Destinations />,
      },
    ],
  },
]);
