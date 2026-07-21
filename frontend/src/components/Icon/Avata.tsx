import { UserRound } from "lucide-react";
import type { avt } from "../../utils/Type";

const AvataBase = ({ size = 45, url = "" }: avt) => {
    return (
        <div
            className="text-white flex justify-center items-center rounded-full bg-gray-400/30 hover:bg-gray-400/40 cursor-pointer overflow-hidden"
            style={{
                width: size,
                height: size,
            }}
        >
            {url ? (
                <img
                    src={url}
                    alt="avatar"
                    className="w-full h-full object-cover"
                />
            ) : (
                <UserRound size={size * 0.65} />
            )}
        </div>
    );
};

export default AvataBase;
