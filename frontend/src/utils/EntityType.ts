// --- ENUMS TẠM THỜI (Bạn có thể điều chỉnh lại giá trị cho đúng với Backend) ---
export type FriendStatus =
    | "PENDING"
    | "ACCEPTED"
    | "DECLINED"
    | "BLOCKED"
    | string;
export type NotificationType = string;
export type Privacy = "PUBLIC" | "PRIVATE" | "FRIENDS" | string;
export type PostStatus = string;
export type MediaType = "IMAGE" | "VIDEO" | string;
export type Reaction =
    | "LIKE"
    | "LOVE"
    | "HAHA"
    | "WOW"
    | "SAD"
    | "ANGRY"
    | string;
export type Gender = "MALE" | "FEMALE" | "OTHER" | string;
export type Role = "USER" | "ADMIN" | string;

// --- INTERFACES ---

export interface Comment {
    id: number;
    content?: string;
    user?: User;
    post?: Post;
    comment?: Comment;
    createAt?: string;
    updateAt?: string;
    deleteAt?: string;
}

export interface Conversation {
    id: number;
    name?: string;
    isGroup?: boolean;
    creator?: User;
    createAt?: string;
    updateAt?: string;
}

export interface ConversationMember {
    conversation?: Conversation;
    user?: User;
    joinedAt?: string;
}

export interface ConversationMemberId {
    conversation: number;
    user: number;
}

export interface Follow {
    id: number;
    follower?: User;
    following?: User;
    createAt?: string;
}

export interface Friend {
    id: number;
    user1?: User;
    user2?: User;
    status?: FriendStatus;
    createAt?: string;
    updateAt?: string;
    deleteAt?: string;
}

export interface Friendship {
    id: number;
    sender?: User;
    receiver?: User;
    createAt?: string;
}

export interface Hastag {
    id: number;
    type?: string;
    post?: Post;
}

export interface Message {
    id: number;
    conversation?: Conversation;
    sender?: User;
    content?: string;
    mediaUrl?: string;
    emoji?: string;
    isRecalled?: boolean;
    createAt?: string;
}

export interface MessageStatus {
    message?: Message;
    user?: User;
    isSeen?: boolean;
    seenAt?: string;
}

export interface MessageStatusId {
    message: number;
    user: number;
}

export interface Notification {
    id: number;
    message?: string;
    type?: NotificationType;
    isRead?: boolean;
    action?: string;
    user?: User;
    targetId?: number;
    createdAt?: string;
}

export interface Post {
    id: number;
    caption?: string;
    user?: User;
    privacy?: Privacy;
    status?: PostStatus;
    createAt?: string;
    updateAt?: string;
    deleteAt?: string;
}

export interface PostMedia {
    id: number;
    post?: Post;
    mediaUrl?: string;
    mediaType?: MediaType;
    createAt?: string;
    updateAt?: string;
}

export interface React {
    id: number;
    reaction?: Reaction;
    user?: User;
    post?: Post;
    comment?: Comment;
    createAt?: string;
}

export interface Share {
    id: number;
    caption?: string;
    user?: User;
    post?: Post;
    createAt?: string;
    updateAt?: string;
}

export interface User {
    id: number;
    username?: string;
    email?: string;
    phone?: string;
    password?: string;
    avata?: string;
    coverPhoto?: string;
    bio?: string;
    dod?: string; // Date of birth / Date of death
    gender?: Gender;
    role?: Role;
    isVerified?: boolean;
    isActive?: boolean;
    falseLogin?: number;
    lockUntil?: string;
    createAt?: string;
    updateAt?: string;
    deleteAt?: string;
}

export interface RefreshToken {
    id: number;
    token?: string;
    expiryDate?: string;
    revoke?: boolean;
    user?: User;
}
