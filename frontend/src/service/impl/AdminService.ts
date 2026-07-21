import { http } from "../http/http";
import type { Role, PostStatus } from "../../utils/EntityType";
import type { ApiDataResponse, Page, UserResponse, PostResponse } from "../../utils/ResponType";

export const getAllUsers = (page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(`/admin/users?page=${page}&size=${size}`);
};

export const filterUsersByActive = (isActive: boolean, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(
        `/admin/users/filter/active?isActive=${isActive}&page=${page}&size=${size}`
    );
};

export const filterUsersByRole = (role: Role, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(
        `/admin/users/filter/role?role=${encodeURIComponent(role)}&page=${page}&size=${size}`
    );
};

export const lockOrUnlockUser = (userId: number, isActive: boolean) => {
    return http.put<ApiDataResponse<boolean>>(`/admin/users/${userId}/status?isActive=${isActive}`);
};

export const getAllPosts = (page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<PostResponse>>>(`/admin/posts?page=${page}&size=${size}`);
};

export const filterPostsByStatus = (status: PostStatus, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<PostResponse>>>(
        `/admin/posts/filter/status?status=${encodeURIComponent(status)}&page=${page}&size=${size}`
    );
};

export const deletePost = (postId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/admin/posts/${postId}`);
};

export const getStatistics = (startDate: string, endDate: string) => {
    return http.get<ApiDataResponse<Record<string, number>>>(
        `/admin/statistics?startDate=${encodeURIComponent(startDate)}&endDate=${encodeURIComponent(endDate)}`
    );
};
