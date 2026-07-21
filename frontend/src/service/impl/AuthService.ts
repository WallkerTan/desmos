import { http } from "../http/http";
import type { LoginRequest, AccountRequest } from "../../utils/RequestType";
import type { ApiDataResponse, AuthResponse, UserResponse } from "../../utils/ResponType";

export const login = (request: LoginRequest) => {
    return http.post<ApiDataResponse<AuthResponse>>("/auth/login", request);
};

export const register = (account: AccountRequest) => {
    return http.post<ApiDataResponse<UserResponse>>("/auth/register", account);
};

export const logout = () => {
    return http.post<ApiDataResponse<void>>("/auth/logout");
};

export const refreshToken = (token: string) => {
    return http.post<ApiDataResponse<AuthResponse>>(`/auth/refresh?refreshToken=${encodeURIComponent(token)}`);
};

export const forgotPassword = (email: string) => {
    return http.post<ApiDataResponse<boolean>>(`/auth/forgot-password?email=${encodeURIComponent(email)}`);
};

export const resetPassword = (email: string, newPassword: string) => {
    return http.post<ApiDataResponse<boolean>>(
        `/auth/reset-password?email=${encodeURIComponent(email)}&newPassword=${encodeURIComponent(newPassword)}`
    );
};

export const seedUsers = () => {
    return http.post<ApiDataResponse<UserResponse[]>>("/auth/seed");
};
