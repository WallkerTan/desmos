import React from "react";
import Navbar from "./../../components/bar/Navbar";
import Sidebar from './../../components/bar/Sidebar';
import RightSidebar from './../../components/bar/RightSidebar';
import HomeMainPage from './../../page/Private/HomeMainPage';

const HomeLayout = () => {
    return (
        <div className="w-full bg-red-500 relative">
            <Navbar />
            <Sidebar />
            <RightSidebar />
            <HomeMainPage />
        </div>
    );
};

export default HomeLayout;
