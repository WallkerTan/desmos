import React from "react";
import InputBase from "../input/inputBase";
import ButtonBase from "../button/ButtonBase";
import "./formLogin.css";
import { useNavigate } from "react-router-dom";
const LoginForm = () => {
    const navigate = useNavigate();
    return (
        <form className="w-full flex flex-col items-center">
            <div className="">
                <div>Login to</div>
                <div className="desmos text-6xl">DESMOS</div>
            </div>
            <div className="w-full ">
                <InputBase label="email" type="text" name="email" id="email" />
                <InputBase
                    label="password"
                    type="password"
                    name="password"
                    id="password"
                />
            </div>
            <ButtonBase label="Login" type="submit" />
            <div className="fgp">Forgotten password</div>
            <button
                className="cna"
                type="button"
                onClick={() => navigate("/register")}
            >
                Create new account
            </button>
            <small className="w-[80%]">
                Tiếp tục nghĩa là bạn đồng ý với Điều khoản dịch vụ, Chính sách
                bảo mật và Sử dụng Cookie của chúng tôi.
            </small>
        </form>
    );
};

export default LoginForm;
