import { createBrowserRouter } from "react-router-dom";
import Notfound from "../page/Notfound/Notfound";
import AuthenticationLayout from "../layout/Auth/AuthenticationLayout";
import RegisterForm from "./../components/form/RegisterForm";
import LoginForm from "../components/form/LoginForm";
import HomeLayout from "./../layout/Private/HomeLayout";

const Router_Desmos = createBrowserRouter([
    {
        path: "/desmos.com",
        element: <AuthenticationLayout />,
        children: [
            {
                path: "login",
                element: <LoginForm />,
            },
            {
                path: "register",
                element: <RegisterForm />,
            },
        ],
    },
    {
        path: "/desmos.com/home",
        element: <HomeLayout />,
    },
    {
        path: "*",
        element: <Notfound />,
    },
]);
export default Router_Desmos;
