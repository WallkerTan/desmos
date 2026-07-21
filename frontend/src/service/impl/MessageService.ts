import { http } from "../http/http";
import type { Conversation, Message } from "../../utils/EntityType";
import type { ApiDataResponse, Page } from "../../utils/ResponType";

export const createOrGetPrivateConversation = (currentUserId: number, userId2: number) => {
    return http.post<ApiDataResponse<Conversation>>(
        `/messages/conversations/private/${userId2}?currentUserId=${currentUserId}`
    );
};

export const createGroupConversation = (currentUserId: number, name: string, memberIds: number[]) => {
    return http.post<ApiDataResponse<Conversation>>(
        `/messages/conversations/group?currentUserId=${currentUserId}&name=${encodeURIComponent(name)}`,
        memberIds
    );
};

export const sendMessage = (
    currentUserId: number,
    conversationId: number,
    content?: string,
    mediaUrl?: string,
    emoji?: string
) => {
    const params = new URLSearchParams();
    params.append("currentUserId", currentUserId.toString());
    params.append("conversationId", conversationId.toString());
    if (content !== undefined) params.append("content", content);
    if (mediaUrl !== undefined) params.append("mediaUrl", mediaUrl);
    if (emoji !== undefined) params.append("emoji", emoji);

    return http.post<ApiDataResponse<Message>>(`/messages?${params.toString()}`);
};

export const recallMessage = (currentUserId: number, messageId: number) => {
    return http.delete<ApiDataResponse<boolean>>(`/messages/${messageId}/recall?currentUserId=${currentUserId}`);
};

export const getMessages = (currentUserId: number, conversationId: number, page: number = 0, size: number = 20) => {
    return http.get<ApiDataResponse<Page<Message>>>(
        `/messages/conversations/${conversationId}?currentUserId=${currentUserId}&page=${page}&size=${size}`
    );
};

export const markConversationAsSeen = (currentUserId: number, conversationId: number) => {
    return http.put<ApiDataResponse<boolean>>(`/messages/conversations/${conversationId}/seen?currentUserId=${currentUserId}`);
};
