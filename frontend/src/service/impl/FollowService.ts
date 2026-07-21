import { http } from "../http/http";
import type { ApiDataResponse, Page, UserResponse } from "../../utils/ResponType";

export const followUser = (currentUserId: number, followingId: number) => {
    return http.post<ApiDataResponse<boolean>>(`/follows/${followingId}?currentUserId=${currentUserId}`);
};

export const unfollowUser = (currentUserId: number, followingId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/follows/${followingId}?currentUserId=${currentUserId}`);
};

export const getFollowers = (currentUserId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(
        `/follows/followers?currentUserId=${currentUserId}&page=${page}&size=${size}`
    );
};

export const getFollowing = (currentUserId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<UserResponse>>>(
        `/follows/following?currentUserId=${currentUserId}&page=${page}&size=${size}`
    );
};
