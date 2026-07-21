import { http } from "../http/http";
import type { CommentRequest } from "../../utils/RequestType";
import type { ApiDataResponse, Page, CommentResponse } from "../../utils/ResponType";

export const createComment = (currentUserId: number, request: CommentRequest) => {
    return http.post<ApiDataResponse<CommentResponse>>(`/comments?currentUserId=${currentUserId}`, request);
};

export const updateComment = (currentUserId: number, commentId: number, content: string) => {
    return http.put<ApiDataResponse<CommentResponse>>(`/comments/${commentId}?currentUserId=${currentUserId}`, content, {
        headers: {
            "Content-Type": "text/plain",
        },
    });
};

export const deleteComment = (currentUserId: number, commentId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/comments/${commentId}?currentUserId=${currentUserId}`);
};

export const getCommentsByPostId = (postId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<CommentResponse>>>(`/comments/posts/${postId}?page=${page}&size=${size}`);
};

export const getRepliesByCommentId = (commentId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<CommentResponse>>>(`/comments/${commentId}/replies?page=${page}&size=${size}`);
};
