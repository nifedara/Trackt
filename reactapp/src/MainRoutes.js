import { createBrowserRouter } from "react-router-dom"
import { LoginPage, LandingPage, SignUpPage } from "./pages"

export const Routes = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage />
    },
    {
        path: "/login",
        element: <LoginPage />
    },
    {
        path: "/signup",
        element: <SignUpPage />
    }
])