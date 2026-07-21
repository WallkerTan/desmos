import { http } from "../http/http";
import type { ApiDataResponse, Page, NotificationResponse } from "../../utils/ResponType";

export const getNotifications = (currentUserId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<NotificationResponse>>>(
        `/notifications?currentUserId=${currentUserId}&page=${page}&size=${size}`
    );
};

export const markNotificationAsRead = (currentUserId: number, notificationId: number) => {
    return http.put<ApiDataResponse<boolean>>(`/notifications/${notificationId}/read?currentUserId=${currentUserId}`);
};

export const markAllNotificationsAsRead = (currentUserId: number) => {
    return http.put<ApiDataResponse<boolean>>(`/notifications/read-all?currentUserId=${currentUserId}`);
};
