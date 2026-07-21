
// ==========================================
//                 RESPONSES
// ==========================================

import type { FriendStatus, Gender, MediaType, NotificationType, PostStatus, Privacy, Reaction, Role } from "./EntityType";

export interface AuthResponse {
    accessToken?: string;
    refreshToken?: string;
    user?: UserResponse;
}

export interface CommentResponse {
    id?: number;
    content?: string;
    user?: UserResponse;
    postId?: number;
    parentCommentId?: number;
    createAt?: string;
    updateAt?: string;
}

export interface FriendResponse {
    id?: number;
    user1?: UserResponse;
    user2?: UserResponse;
    status?: FriendStatus;
    createAt?: string;
    updateAt?: string;
}

export interface FriendshipResponse {
    id?: number;
    sender?: UserResponse;
    receiver?: UserResponse;
    createAt?: string;
}

export interface HastagResponse {
    id?: number;
    type?: string;
    postId?: number;
}

export interface NotificationResponse {
    id?: number;
    message?: string;
    type?: NotificationType;
    isRead?: boolean;
    action?: string;
    user?: UserResponse;
    targetId?: number;
    createdAt?: string;
}

export interface PostMediaResponse {
    id?: number;
    postId?: number;
    mediaUrl?: string;
    mediaType?: MediaType;
    createAt?: string;
    updateAt?: string;
}

export interface PostResponse {
    id?: number;
    caption?: string;
    user?: UserResponse;
    privacy?: Privacy;
    status?: PostStatus;
    createAt?: string;
    updateAt?: string;
}

export interface ReactResponse {
    id?: number;
    reaction?: Reaction;
    user?: UserResponse;
    postId?: number;
    commentId?: number;
    createAt?: string;
}

export interface ShareResponse {
    id?: number;
    caption?: string;
    user?: UserResponse;
    post?: PostResponse;
    createAt?: string;
    updateAt?: string;
}

export interface UserResponse {
    id?: number;
    username?: string;
    email?: string;
    phone?: string;
    avata?: string;
    coverPhoto?: string;
    bio?: string;
    dod?: string;
    gender?: Gender;
    role?: Role;
    isVerified?: boolean;
    isActive?: boolean;
    createAt?: string;
    updateAt?: string;
}

export interface ApiDataResponse<T> {
    status: boolean;
    message: string;
    data: T;
    httpStatus: string;
}

export interface Page<T> {
    content: T[];
    pageable?: any;
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    number: number;
    sort?: any;
    numberOfElements: number;
    first: boolean;
    empty: boolean;
}

