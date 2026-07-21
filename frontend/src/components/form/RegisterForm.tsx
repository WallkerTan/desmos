import React from "react";
import InputBase from "../input/inputBase";
import ButtonBase from "../button/ButtonBase";
import "./formLogin.css";
import { useNavigate } from "react-router-dom";
const RegisterForm = () => {
    const navigate = useNavigate();
    return (
        <form className="w-full flex flex-col items-center">
            <div className="">
                <div>Create new account</div>
                <div className="desmos text-6xl">DESMOS</div>
            </div>
            <div className="w-full ">
                <InputBase
                    label="username"
                    type="text"
                    name="username"
                    id="username"
                />
                <InputBase label="email" type="text" name="email" id="email" />
                <InputBase label="phone" type="text" name="phone" id="phone" />
                <InputBase
                    label="password"
                    type="password"
                    name="password"
                    id="password"
                />
            </div>
            <ButtonBase type="submit" label="Create account" />
            <button className="cna" onClick={() => navigate("/login")}>
                Login
            </button>
            <small className="w-[80%] mt-2 cursor-pointer">
                Tiếp tục nghĩa là bạn đồng ý với Điều khoản dịch vụ, Chính sách
                bảo mật và Sử dụng Cookie của chúng tôi.
            </small>
        </form>
    );
};

export default RegisterForm;
