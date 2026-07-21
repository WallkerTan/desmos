import React from "react";
import { Ellipsis } from "lucide-react";
const ButtonClose = () => {
    return (
        <div className="w-[30px] h-[30px] rounded-[50%] flex items-center justify-center absolute right-2 hover:bg-white/20">
            <Ellipsis />
        </div>
    );
};

export default ButtonClose;
