import React from "react";

import "./Footer.css";
import { Copyright } from "lucide-react";
import LogoD from "../../../../image/logo-d.png";
import Social from "./../Contact/Social";
const Footer = () => {
    return (
        <div className="bgfooter">
            <div className="">
                <div className="flex w-full justify-between border-b border-white py-5">
                    <div className="flex items-center gap-5">
                        <img src={LogoD} alt="" width={70} />
                        <div className="text-white text-2xl">
                            <div>
                                Công Ty Đa Vũ Trụ và kết Nối Liên Sao DESMOS
                            </div>
                            <div>
                                Desmos Multi-Universe and Inter-Star Connection
                                Company
                            </div>
                        </div>
                    </div>
                    <Social />
                </div>
                <div className="w-full flex flex-wrap gap-10 p-5 pl-10">
                    <div className=" max-w-[400px]">
                        <div className="text-white/70 text-[20px]">
                            Trụ sở chính
                        </div>
                        <div className="text-white">Hành tinh TSX</div>
                    </div>
                    <div className=" max-w-[400px]">
                        <div className="text-white/70 text-[20px]">
                            Cơ sở tại Hệ Mặt Trời
                        </div>
                        <div className="text-white">Trái đất</div>
                    </div>
                    <div className=" max-w-[400px]">
                        <div className="text-white/70 text-[20px]">Email</div>
                        <div className="text-white">
                            Tanpham220106z@gmail.com
                        </div>
                    </div>
                    <div className=" max-w-[400px] pl-45">
                        <div className="text-white/70 text-[20px]">Liên hệ</div>
                        <div className="text-white">0966247783</div>
                        <div className="text-white">DesmosGloble@gmail.com</div>
                    </div>
                </div>
            </div>
            <div className="bg-gray-50/20 w-full flex justify-center text-white items-center gap-2">
                <Copyright size={20} />
                <div>DESMOS 2026 Multi-Universe. ALL rights reserved</div>
            </div>
        </div>
    );
};

export default Footer;
