import { http } from "../http/http";
import type { PostMedia } from "../../utils/EntityType";
import type { PostMediaRequest } from "../../utils/RequestType";
import type { ApiDataResponse, Page, PostMediaResponse } from "../../utils/ResponType";

export const createMedia = (request: PostMediaRequest) => {
    return http.post<ApiDataResponse<PostMedia>>("/post-medias", request);
};

export const getMediaByPostId = (postId: number) => {
    return http.get<ApiDataResponse<Page<PostMediaResponse>>>(`/post-medias/posts/${postId}`);
};

export const deleteMedia = (id: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/post-medias/${id}`);
};

export const downloadMedia = (id: number) => {
    return http.get<ApiDataResponse<boolean>>(`/post-medias/${id}/download`);
};
