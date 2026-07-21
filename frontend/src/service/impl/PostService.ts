import { http } from "../http/http";
import type { Post } from "../../utils/EntityType";
import type { PostRequest } from "../../utils/RequestType";
import type { ApiDataResponse, Page } from "../../utils/ResponType";

export const createPost = (request: PostRequest) => {
    return http.post<ApiDataResponse<Post>>("/posts", request);
};

export const getPostById = (id: number) => {
    return http.get<ApiDataResponse<Post>>(`/posts/${id}`);
};

export const searchPosts = (caption: string, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<Post>>>(
        `/posts/search?caption=${encodeURIComponent(caption)}&page=${page}&size=${size}`
    );
};

export const updatePost = (id: number, request: PostRequest) => {
    return http.put<ApiDataResponse<boolean>>(`/posts/${id}`, request);
};

export const deletePost = (id: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/posts/${id}`);
};
