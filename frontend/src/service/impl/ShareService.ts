import { http } from "../http/http";
import type { ShareRequest } from "../../utils/RequestType";
import type { ApiDataResponse, ShareResponse } from "../../utils/ResponType";

export const sharePost = (currentUserId: number, request: ShareRequest) => {
    return http.post<ApiDataResponse<ShareResponse>>(`/shares?currentUserId=${currentUserId}`, request);
};

export const deleteShare = (currentUserId: number, shareId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/shares/${shareId}?currentUserId=${currentUserId}`);
};
