import { http } from "../http/http";
import type { User } from "../../utils/EntityType";
import type { UserRequest } from "../../utils/RequestType";
import type { ApiDataResponse, Page, UserResponse } from "../../utils/ResponType";

export const changePassword = (currentUserId: number, newPassword: string) => {
    return http.put<ApiDataResponse<boolean>>(
        `/users/me/password?currentUserId=${currentUserId}&newPassword=${encodeURIComponent(newPassword)}`
    );
};

export const updateProfile = (currentUserId: number, request: UserRequest) => {
    return http.put<ApiDataResponse<User>>(`/users/me?currentUserId=${currentUserId}`, request);
};

export const searchUsers = (username: string) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(`/users/search?username=${encodeURIComponent(username)}`);
};
