import React from "react";
import type { logoprop } from "../../utils/Type";
const Logo = ({ url, w = 30, h = 30 }: logoprop) => {
    return <img width={w} height={h} src={url} alt="friends" />;
};

export default Logo;
