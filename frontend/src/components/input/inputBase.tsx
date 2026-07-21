import React from "react";
import styled from "styled-components";
import type { InputBaseProps } from "../../utils/Type";

const InputBase = ({ label, type = "text", name, id }: InputBaseProps) => {
    return (
        <StyledWrapper className=" px-10">
            <div className="form-control  bg-gray-500/10">
                <input type={type} name={name} required />
                <label htmlFor={id}>
                    {label.split("").map((char, index) => (
                        <span
                            key={index}
                            style={{ transitionDelay: `${index * 50}ms` }}
                        >
                            {char}
                        </span>
                    ))}
                </label>
            </div>
        </StyledWrapper>
    );
};

const StyledWrapper = styled.div`
    .form-control {
        position: relative;
        margin: 20px 0 40px;
        width: 100%;
    }

    .form-control input {
        background-color: transparent;
        border: 0;
        border-bottom: 2px black solid;
        display: block;
        width: 100%;
        padding: 15px 0;
        font-size: 18px;
        color: black;
    }

    .form-control input:focus,
    .form-control input:valid {
        outline: 0;
        border-bottom-color: #33ffff;
    }

    .form-control label {
        position: absolute;
        top: 15px;
        left: 0;
        pointer-events: none;
    }

    .form-control label span {
        display: inline-block;
        font-size: 18px;
        min-width: 5px;
        color: black;
        transition: 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
    }

    .form-control input:focus + label span,
    .form-control input:valid + label span {
        color: #33ffff;
        transform: translateY(-30px);
    }
`;

export default InputBase;
/* https://grabient.com/IwOgDA7AzANCBswCccCsEBMMC0YTDDDg3hVxA1SPNWFnIBYxgc94IXx3XwAOI-ASA?angle=45 */
