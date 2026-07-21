import React from "react";
import styled from "styled-components";
import { Search } from 'lucide-react';
const SearchBase = () => {
    return (
        <StyledWrapper className="text-white">
            <div className="container-input flex justify-center items-center">
                <input

                    type="text"
                    placeholder="Search"
                    name="text"
                    className="input bg-white/30"
                />
               <Search className="absolute left-1"/>
            </div>
        </StyledWrapper>
    );
};

const StyledWrapper = styled.div`
    .container-input {
        position: relative;
    }

    .input {

        width: 150px;
        padding: 10px 0px 10px 40px;
        border-radius: 9999px;
        border: solid 1px #333;
        transition: all 0.2s ease-in-out;
        outline: none;
        opacity: 0.8;
    }

    .input:focus {
        border: solid 1px white;
        background-color: #7d7d7d;
        opacity: 1;
        width: 250px;
    }
`;

export default SearchBase;
