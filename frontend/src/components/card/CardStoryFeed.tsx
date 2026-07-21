import React from "react";
import type { cardstoryprop } from "../../utils/Type";
import { Plus } from "lucide-react";

import "../../index.css";
const CardStoryFeed = ({ type = 1 }: cardstoryprop) => {
    const renderCard = () => {
        switch (type) {
            case 1:
                return (
                    <div className="w-[20%] shrink-0 h-full">
                        <div className=" cursor-pointer h-full rounded-2xl bg-[#252728] relative">
                            <img
                                src="https://i.pinimg.com/736x/a7/94/fb/a794fb1f3753100fe76309793d1882a0.jpg"
                                className="w-full h-[70%]  rounded-t-2xl"
                            />
                            <div className="w-full h-[30%] rounded-b-2xl bg-[#252728] flex justify-center items-center">
                                Tạo tin
                            </div>
                            <div className="w-[40px] h-[40px] rounded-[50%] flex justify-center items-center border-4 border-[#252728] bg-blue-500 absolute bottom-[20%] left-[35%]">
                                <Plus />
                            </div>
                        </div>
                    </div>
                );
            case 2:
                return (
                    <div className="w-[20%] shrink-0 h-full">
                        <div className="cursor-pointer h-full rounded-2xl  bg-[#252728] relative">
                            <div className="z-1 w-[42px] h-[42px] flex justify-center items-center border-blue-500 border-4 bg-green-500 rounded-[50%]  absolute top-1 left-1 ">
                                <img
                                    src="https://i.pinimg.com/736x/a7/94/fb/a794fb1f3753100fe76309793d1882a0.jpg"
                                    className="w-[35px] h-[35px] border-3 border-blue-500 rounded-[50%]"
                                />
                            </div>{" "}
                            <video
                                src="https://res.cloudinary.com/dx4ym1xrj/video/upload/v1784443683/8c3bc8c2-3a84-428d-9cc8-cb94c256df51_bqcogf.mp4"
                                autoPlay
                                muted
                                loop
                                playsInline
                                className="absolute inset-0 rounded-2xl w-full h-full object-cover"
                            >
                                <source
                                    src="https://res.cloudinary.com/dx4ym1xrj/video/upload/v1784443683/8c3bc8c2-3a84-428d-9cc8-cb94c256df51_bqcogf.mp4"
                                    type="video/mp4"
                                />
                            </video>
                            <div className=" w-full font-sans flex justify-center absolute bottom-6">
                                Donal trump
                            </div>
                        </div>
                    </div>
                );

            case 3:
                return (
                    <div className="w-[20%] shrink-0 h-full">
                        <div className="cursor-pointer h-full rounded-2xl  bg-[#252728] relative">
                            <div className=" z-1 w-[42px] h-[42px] flex justify-center items-center border-blue-500 borded-4 bg-green-500 rounded-[50%]  absolute top-1 left-1 ">
                                <img
                                    src="https://i.pinimg.com/736x/a7/94/fb/a794fb1f3753100fe76309793d1882a0.jpg"
                                    className="w-[35px] h-[35px] border-3 border-blue-500 rounded-[50%]"
                                />
                            </div>
                            <img
                                src="https://i.pinimg.com/736x/23/65/20/236520d1c3d0699770ed92d05bb4836a.jpg"
                                alt=""
                                className="blur-sm rounded-2xl absolute top-0 left-0 h-full w-full"
                            />
                            <div className="hide-scrollbar w-[90%] max-h-[55%] absolute left-2 top-12 bg-[#252728] rounded-b-2xl overflow-auto rounded-tr-2xl p-2">
                                "Gió đưa chân đến nơi xa, lòng đưa mình đến
                                những miền chưa biết."
                            </div>
                            <div className=" w-full font-sans flex justify-center absolute bottom-6">
                                Donal trump
                            </div>
                        </div>
                    </div>
                );

            default:
                return (
                    <div className="w-[20%] shrink-0 h-full">
                        <div className=" cursor-pointer h-full rounded-2xl bg-green-500 relative">
                            <img
                                src="https://i.pinimg.com/736x/a7/94/fb/a794fb1f3753100fe76309793d1882a0.jpg"
                                className="w-full h-[70%]  rounded-t-2xl"
                            />
                            <div className="w-full h-[30%] rounded-b-2xl bg-[#252728] flex justify-center items-center">
                                Tạo tin
                            </div>
                            <div className="w-[40px] h-[40px] rounded-[50%] flex justify-center items-center border-4 border-[#252728] bg-blue-500 absolute bottom-[20%] left-[35%]">
                                <Plus />
                            </div>
                        </div>
                    </div>
                );
        }
    };
    return <>{renderCard()}</>;
};

export default CardStoryFeed;
