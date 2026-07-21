import React from "react";
import styled from "styled-components";
import type { ButtonBaseProps } from "../../utils/Type";

const ButtonBase = ({ label, type = "button", onClick }: ButtonBaseProps) => {
    return (
        <StyledWrapper className="w-full px-10 relative right-2">
            <button className="btn" type={type} onClick={onClick}>
                {" "}
                {label}
            </button>
        </StyledWrapper>
    );
};

const StyledWrapper = styled.div`
    .btn {
        width: 100%;
        height: 2.3em;
        margin: 0.5em;
        background: black;
        color: white;
        border: 1px solid black;
        border-radius: 1px;
        font-size: 20px;
        font-weight: bold;
        cursor: pointer;
        position: relative;
        z-index: 1;
        overflow: hidden;
    }

    button:hover {
        color: black;
        border: 1px solid black;
    }

    button:after {
        content: "";
        background: white;
        position: absolute;
        z-index: -1;
        left: -20%;
        right: -20%;
        top: 0;
        bottom: 0;
        transform: skewX(-45deg) scale(0, 1);
        transition: all 0.5s;
    }

    button:hover:after {
        transform: skewX(-45deg) scale(1, 1);
        -webkit-transition: all 0.5s;
        transition: all 0.5s;
    }
`;

export default ButtonBase;
