import React from "react";
import Logo from "../../components/Icon/Logo";
import CardStoryFeed from "./../../components/card/CardStoryFeed";
import "../../index.css";
const HomeMainPage = () => {
    return (
        <div className="fixed w-[80%] text-white h-[100vh] pl-[18vh] pt-[9vh] bg-[#1c1c1d] absolute right-0">
            <div className="w-[60%] h-full bg-white p-2">
                <div className="w-full h-[8vh] rounded-2xl flex items-center bg-amber-500">
                    <Logo
                        url="https://img.icons8.com/?size=100&id=tZuAOUGm9AuS&format=png&color=000000"
                        w={50}
                        h={50}
                    />
                    <input
                        placeholder="Pham Tan, Hay cho moi nguoi biet ban dang nghi gi!"
                        type="text"
                        className="flex-1 bg-gray-500 rounded-2xl outline-none bg-white/20 px-4 py-2 "
                    />
                    <div className="flex mx-2">
                        <Logo url="https://img.icons8.com/?size=100&id=kZ60xjJXDztQ&format=png&color=000000" />
                        <Logo url="https://img.icons8.com/?size=100&id=1vNMGfe3pMAQ&format=png&color=000000" />
                        <Logo url="https://img.icons8.com/?size=100&id=FFNFFoRb7Psi&format=png&color=000000" />
                    </div>
                </div>
                {/* story */}
                <div className="hide-scrollbar w-full h-[30vh] my-2 flex gap-1 justify-between bg-red-500 overflow-x-auto ">
                    <CardStoryFeed type={1} />
                    <CardStoryFeed type={2} />
                    <CardStoryFeed type={3} />
                    <CardStoryFeed type={2} />
                    <CardStoryFeed type={2} />
                    <CardStoryFeed type={2} />
                </div>
            </div>
        </div>
    );
};

export default HomeMainPage;
