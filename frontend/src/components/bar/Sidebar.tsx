import React from "react";
import Logo from "./../Icon/Logo";
import CardTopSideBar from "./../card/CardTopSideBar";
import CardBottomSidebar from "./../card/CardBottomSidebar";

const Sidebar = () => {
    return (
        <div className="fixed w-[23%] h-[100vh] bg-[#1c1c1d] text-white z-99">
            <div className="w-full max-h-[70%]  mt-[9vh] p-2 overflow-x-hidden border-b-1">
                <div className="w-full p-1 flex items-center gap-5 rounded-2xl hover:bg-white/20 my-2">
                    <Logo
                        url="https://img.icons8.com/?size=100&id=tZuAOUGm9AuS&format=png&color=000000"
                        w={35}
                        h={35}
                    />
                    <div>Pham Nhat Tan</div>
                </div>
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=M16ic8QWK8x6&format=png&color=000000"
                    content="Meta AI"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=69378&format=png&color=000000"
                    content="Bạn bè"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=52494&format=png&color=000000"
                    content="Bảng điều khiển"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=VhrilWh3UcXU&format=png&color=000000"
                    content="Kỉ niệm"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=2hWCXizM86e6&format=png&color=000000"
                    content="Đã lưu"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=4wtEo23zlAMj&format=png&color=000000"
                    content="Nhóm"
                />
                <CardTopSideBar
                    url="https://img.icons8.com/?size=100&id=FFNFFoRb7Psi&format=png&color=000000"
                    content="Thước phim"
                />
            </div>
            <div className="w-full max-h-[30%] p-2  overflow-auto">
                <div className="pl-2 text-white/50">Lối tắt của bạn</div>
                <CardBottomSidebar />
                <CardBottomSidebar />
                <CardBottomSidebar />
            </div>
        </div>
    );
};

export default Sidebar;
