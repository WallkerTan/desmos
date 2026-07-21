
// ==========================================
//                 REQUESTS
// ==========================================

import type { FriendStatus, Gender, MediaType, NotificationType, PostStatus, Privacy, Reaction, Role } from "./EntityType";

export interface AccountRequest {
    username?: string;
    email?: string;
    phone?: string;
    password?: string;
}

export interface CommentRequest {
    content?: string;
    postId?: number;
    parentCommentId?: number;
}

export interface FriendRequest {
    user2Id?: number;
    status?: FriendStatus;
}

export interface FriendshipRequest {
    receiverId?: number;
}

export interface HastagRequest {
    type?: string;
    postId?: number;
}

export interface LoginRequest {
    email?: string;
    password?: string;
}

export interface NotificationRequest {
    message?: string;
    type?: NotificationType;
    isRead?: boolean;
    action?: string;
    userId?: number;
    targetId?: number;
}

export interface PostMediaRequest {
    postId?: number;
    mediaUrl?: string;
    mediaType?: MediaType;
}

export interface PostRequest {
    caption?: string;
    privacy?: Privacy;
    status?: PostStatus;
}

export interface ReactRequest {
    reaction?: Reaction;
    postId?: number;
    commentId?: number;
}

export interface ShareRequest {
    caption?: string;
    postId?: number;
}

export interface UserRequest {
    username?: string;
    email?: string;
    phone?: string;
    password?: string;
    avata?: string;
    coverPhoto?: string;
    bio?: string;
    dod?: string;
    gender?: Gender;
    role?: Role;
}
