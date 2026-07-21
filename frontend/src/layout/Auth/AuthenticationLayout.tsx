import React from "react";
import TheDesmos from "./../../page/Auth/TheDesmos";
import Logo from "./../../components/Icon/Logo";
import Footer from "./../../components/footer/Footer";
import { Outlet } from "react-router-dom";

const AuthenticationLayout = () => {
    return (
        <div className="overflow-hidden">
            <div className="h flex relative">
                <div className=" w-[60%] min-h-[100vh] flex justify-center items-center border-r-2">
                    <Logo />
                    <TheDesmos />
                    <div className="w-[20%] absolute bottom-20 left-10 text-7xl">
                        Explore the things{" "}
                        <div className="text-blue-500"> you love.</div>
                    </div>
                    <div className="w-[35%] absolute top-5 right-150 text-6xl">
                        See everyday moments from your <div className="text-red-500">close friends.</div>
                    </div>
                </div>
                <div className=" w-[40%] flex justify-center items-center">
                    <Outlet />
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default AuthenticationLayout;
