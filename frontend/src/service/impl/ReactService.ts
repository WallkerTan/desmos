import { http } from "../http/http";
import type { ReactRequest } from "../../utils/RequestType";
import type { ApiDataResponse, ReactResponse } from "../../utils/ResponType";

export const reactPostOrComment = (currentUserId: number, request: ReactRequest) => {
    return http.post<ApiDataResponse<ReactResponse>>(`/reacts?currentUserId=${currentUserId}`, request);
};

export const getReactionsByPostId = (postId: number) => {
    return http.get<ApiDataResponse<ReactResponse[]>>(`/reacts/posts/${postId}`);
};

export const getReactionsByCommentId = (commentId: number) => {
    return http.get<ApiDataResponse<ReactResponse[]>>(`/reacts/comments/${commentId}`);
};
