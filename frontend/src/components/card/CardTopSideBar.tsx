import React from "react";
import type { cardtopsidebarprop } from "../../utils/Type";
import Logo from "../Icon/Logo";

const CardTopSideBar = ({ url, content = "btn" }: cardtopsidebarprop) => {
    return (
        <div className="w-full p-2 flex items-center gap-5 rounded-2xl hover:bg-white/20 my-2">
            <Logo url={url} />
            <div>{content}</div>
        </div>
    );
};

export default CardTopSideBar;
