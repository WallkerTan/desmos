export interface InputBaseProps {
    label: string;
    type?: string | "password" | "text";
    name: string;
    id: string;
}
export interface ButtonBaseProps {
    label: string;
    type?: "button" | "submit" | "reset";
    onClick?: () => void;
}
export interface FormControll {
    EForm: "login" | "register";
}
export interface avt {
    size?: number;
    url?: string;
}
export interface logoprop {
    url: string;
    w?: number;
    h?: number;
}
export interface cardtopsidebarprop {
    url: string;
    content?: string;
}
export interface cardstoryprop {
    type?: number;
}
