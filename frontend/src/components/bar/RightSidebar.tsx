import React from "react";
import CardTopRightBar from "./../card/CardTopRightBar";
import CardMidRightBar from "./../card/CardMidRightBar";
import CardBottomRightBar from "./../card/CardBottomRightBar";

const RightSidebar = () => {
    return (
        <div className="w-[23%] h-[100vh] pt-[9vh]  text-white bg-[#1c1c1d] fixed top-0 right-0 z-99">
            <div className="w-full h-full overflow-auto">
                {/* tài trợ */}
                <div className="w-full h-[70%] p-4 border-b-1">
                    <div className="text-white/50">Được tài trợ</div>
                    <CardTopRightBar />
                    <CardTopRightBar />
                </div>
                {/* sinh nhật */}
                <div className="w-full  p-4 overflow-auto border-b-1">
                    <div className="text-white/50">Sinh nhật</div>
                    <CardMidRightBar />
                </div>
                {/* bạn bè */}
                <div className="w-full  p-4 overflow-auto ">
                    <div className="text-white/50">Bạn bè</div>
                    <CardBottomRightBar />
                    <CardBottomRightBar />
                    <CardBottomRightBar />
                    <CardBottomRightBar />
                    <CardBottomRightBar />
                </div>
            </div>
        </div>
    );
};

export default RightSidebar;
