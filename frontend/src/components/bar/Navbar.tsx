import React from "react";
import SearchBase from "./../input/SearchBase";
import AvataBase from "./../Icon/Avata";

import {
    House,
    Columns3Cog,
    TvMinimalPlay,
    Store,
    LayoutGrid,
    Gamepad,
    MessageCircleMore,
    Bell,
} from "lucide-react";

const Navbar = () => {
    return (
        <div className="fixed top-0 left-0 w-full h-[9vh] bg-[#252728] flex justify-between  z-100">
            <div className="w-[20%] flex justify-center items-center gap-2 px-5 ">
                <div className="flex justify-center items-center font-black rounded-[50%] text-4xl text-white">
                    D
                </div>
                <SearchBase />
            </div>
            <div className="flex w-[40%] justify-between">
                <div className="w-[15%] flex justify-center items-center text-white hover:border-b-1 border-white hover:bg-gray-400/30">
                    <House size={30} />
                </div>
                <div className="w-[15%] flex justify-center items-center text-white hover:border-b-1 border-white hover:bg-gray-400/30">
                    <Columns3Cog size={30} />
                </div>
                <div className="w-[15%] flex justify-center items-center text-white hover:border-b-1 border-white hover:bg-gray-400/30">
                    <TvMinimalPlay size={30} />
                </div>
                <div className="w-[15%] flex justify-center items-center text-white hover:border-b-1 border-white hover:bg-gray-400/30">
                    <Store size={30} />
                </div>
                <div className="w-[15%] flex justify-center items-center text-white hover:border-b-1 border-white hover:bg-gray-400/30">
                    <Gamepad size={30} />
                </div>
            </div>
            <div className="w-[20%] flex items-center justify-end gap-2 px-5">
                <div className="w-[40px] h-[40px] text-white flex justify-center items-center rounded-[50%] bg-gray-400/30 hover:bg-gray-400/40 cursor-pointer">
                    <LayoutGrid size={25} />
                </div>
                <div className="w-[40px] h-[40px] text-white flex justify-center items-center rounded-[50%] bg-gray-400/30 hover:bg-gray-400/40 cursor-pointer">
                    <MessageCircleMore size={25} />
                </div>
                <div className="w-[40px] h-[40px] text-white flex justify-center items-center rounded-[50%] bg-gray-400/30 hover:bg-gray-400/40 cursor-pointer">
                    <Bell size={25} />
                </div>
                <AvataBase size={40}/>
            </div>
        </div>
    );
};

export default Navbar;
