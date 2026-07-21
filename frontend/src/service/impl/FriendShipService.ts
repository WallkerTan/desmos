import { http } from "../http/http";
import type { ApiDataResponse, Page, FriendResponse, FriendshipResponse } from "../../utils/ResponType";

export const getFriends = (currentUserId: number) => {
    return http.get<ApiDataResponse<FriendResponse[]>>(`/friends?currentUserId=${currentUserId}`);
};

export const sendFriendRequest = (currentUserId: number, receiverId: number) => {
    return http.post<ApiDataResponse<FriendshipResponse>>(`/friends/requests/${receiverId}?currentUserId=${currentUserId}`);
};

export const acceptFriendRequest = (currentUserId: number, senderId: number) => {
    return http.post<ApiDataResponse<FriendResponse>>(`/friends/requests/${senderId}/accept?currentUserId=${currentUserId}`);
};

export const rejectFriendRequest = (currentUserId: number, senderId: number) => {
    return http.post<ApiDataResponse<boolean>>(`/friends/requests/${senderId}/reject?currentUserId=${currentUserId}`);
};

export const cancelFriendRequest = (currentUserId: number, receiverId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/friends/requests/${receiverId}?currentUserId=${currentUserId}`);
};

export const unfriend = (currentUserId: number, friendId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/friends/${friendId}?currentUserId=${currentUserId}`);
};

export const blockUser = (currentUserId: number, blockedId: number) => {
    return http.post<ApiDataResponse<boolean>>(`/friends/${blockedId}/block?currentUserId=${currentUserId}`);
};

export const unblockUser = (currentUserId: number, blockedId: number) => {
    return http.post<ApiDataResponse<boolean>>(`/friends/${blockedId}/unblock?currentUserId=${currentUserId}`);
};

export const getPendingRequests = (currentUserId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<FriendshipResponse>>>(
        `/friends/requests/pending?currentUserId=${currentUserId}&page=${page}&size=${size}`
    );
};

export const getBlockedUsers = (currentUserId: number) => {
    return http.get<ApiDataResponse<FriendResponse[]>>(`/friends/blocked?currentUserId=${currentUserId}`);
};
