# LỘ TRÌNH PHÁT TRIỂN DỰ ÁN DESMOS (14 NGÀY)

> Mục tiêu:
>
> - Hoàn thành **100% Backend** trước.
> - Sau đó phát triển Frontend.
> - Xây dựng theo hướng **Production Ready** để có thể mở rộng thành một mạng xã hội giống Facebook và đưa vào CV.

---

# Tổng quan

| Giai đoạn | Thời gian |
|-----------|------------|
| Backend | Ngày 1 → Ngày 9 |
| Frontend | Ngày 10 → Ngày 14 |

---

# TUẦN 1 - BACKEND

## Ngày 1 - Thiết kế Database & Entity

### Mục tiêu

Hoàn thiện toàn bộ Entity và quan hệ.

### Công việc

- [ ] Thiết kế Database
- [ ] Hoàn thiện Entity
- [ ] Thiết lập Enum
- [ ] Thiết lập Relationship JPA
- [ ] Repository

### Entity

- [ ] User
- [ ] Post
- [ ] PostMedia
- [ ] Comment
- [ ] React
- [ ] Friend
- [ ] Friendship
- [ ] Follow
- [ ] Notification
- [ ] Conversation
- [ ] ConversationMember
- [ ] Message
- [ ] MessageStatus

### Kiểm tra

- [ ] Hibernate Mapping
- [ ] Database Generate
- [ ] Application Start Success

---

## Ngày 2 - Authentication

### Spring Security

- [ ] SecurityConfig
- [ ] JWT Filter
- [ ] JwtService
- [ ] AuthenticationProvider

### Chức năng

- [ ] Register
- [ ] Login
- [ ] Logout
- [ ] Refresh Token
- [ ] Verify Email
- [ ] Forgot Password
- [ ] Change Password

### Hoàn thiện

- [ ] Global Exception
- [ ] Validation
- [ ] Test bằng Postman

---

## Ngày 3 - User Profile

### CRUD Profile

- [ ] View Profile
- [ ] Update Profile
- [ ] Upload Avatar
- [ ] Upload Cover

### Cloudinary

- [ ] Upload Image
- [ ] Delete Image

### Search

- [ ] Search User

---

## Ngày 4 - Friendship

### Friend

- [ ] Send Request
- [ ] Accept
- [ ] Reject
- [ ] Cancel Request
- [ ] Unfriend
- [ ] Block
- [ ] Unblock

### Notification

- [ ] Friend Request
- [ ] Friend Accepted

---

## Ngày 5 - Post

### CRUD

- [ ] Create
- [ ] Update
- [ ] Delete
- [ ] Pin
- [ ] Share

### Media

- [ ] Upload Image
- [ ] Upload Video
- [ ] Delete Media

---

## Ngày 6 - Comment & Reaction

### Comment

- [ ] Create
- [ ] Edit
- [ ] Delete
- [ ] Reply

### Reaction

- [ ] Like
- [ ] Love
- [ ] Haha
- [ ] Wow
- [ ] Sad
- [ ] Angry
- [ ] Undo

### Notification

- [ ] Comment
- [ ] Reply
- [ ] Like

---

## Ngày 7 - Feed

### Follow

- [ ] Follow
- [ ] Unfollow

### Feed

- [ ] News Feed
- [ ] Timeline  
- [ ] Pagination

### Search

- [ ] User
- [ ] Post
- [ ] Hashtag

---

# TUẦN 2

## Ngày 8 - Chat

### Database

- [ ] Conversation
- [ ] Member
- [ ] Message
- [ ] Seen
- [ ] Recall

### REST API

- [ ] Create Conversation
- [ ] Get Messages

---

## Ngày 9 - WebSocket

### WebSocket

- [ ] Connect
- [ ] Send Message
- [ ] Receive Message
- [ ] Seen
- [ ] Recall

### Backend Finish

- [ ] Swagger
- [ ] Validation
- [ ] Exception
- [ ] Testing

---

# FRONTEND

## Ngày 10

### Setup

- [ ] React
- [ ] Vite
- [ ] Tailwind
- [ ] Redux Toolkit
- [ ] Axios
- [ ] React Router
- [ ] Socket

### Layout

- [ ] Navbar
- [ ] Sidebar
- [ ] Right Sidebar
- [ ] Feed Layout

---

## Ngày 11

### Authentication

- [ ] Login
- [ ] Register
- [ ] Forgot Password

### Profile

- [ ] View Profile
- [ ] Edit Profile

---

## Ngày 12

### Feed

- [ ] Create Post
- [ ] Post Card
- [ ] Comment
- [ ] Reply
- [ ] Reaction
- [ ] Share
- [ ] Upload Media

---

## Ngày 13

### Social

- [ ] Friend
- [ ] Follow
- [ ] Search
- [ ] Notification

---

## Ngày 14

### Chat

- [ ] Chat
- [ ] Notification
- [ ] Responsive
- [ ] Bug Fix

---

# Thứ tự code Backend

```
Entity
    ↓
Repository
    ↓
DTO
    ↓
Mapper
    ↓
Service
    ↓
Controller
    ↓
Validation
    ↓
Exception
    ↓
Swagger
    ↓
Postman Test
```

---

# Thứ tự Entity

```
Role
 ↓
User
 ↓
Post
 ↓
PostMedia
 ↓
Comment
 ↓
Reaction
 ↓
Friendship
 ↓
Friend
 ↓
Follow
 ↓
Notification
 ↓
Conversation
 ↓
ConversationMember
 ↓
Message
 ↓
MessageStatus
```

---

# Sau khi hoàn thành 14 ngày

## Phase 2 (Đưa vào CV)

### Authentication

- [ ] Google Login
- [ ] Facebook Login
- [ ] Refresh Token Rotation
- [ ] Two Factor Authentication

---

### User

- [ ] Story
- [ ] Highlight Story
- [ ] Album
- [ ] Archive

---

### Post

- [ ] Draft
- [ ] Schedule Post
- [ ] Poll
- [ ] Check-in
- [ ] Mention User
- [ ] Multiple Hashtag

---

### Media

- [ ] Video Processing
- [ ] Image Compression
- [ ] Thumbnail Generation

---

### Feed

- [ ] Recommendation Algorithm
- [ ] Trending
- [ ] Infinite Scroll
- [ ] Suggested Friends

---

### Chat

- [ ] Voice Message
- [ ] Call
- [ ] Video Call
- [ ] Typing Indicator
- [ ] Online Status
- [ ] Read Receipt

---

### Notification

- [ ] Push Notification
- [ ] Email Notification

---

### Admin

- [ ] Dashboard
- [ ] Analytics
- [ ] User Report
- [ ] Moderation

---

### Performance

- [ ] Redis Cache
- [ ] Docker
- [ ] Docker Compose
- [ ] CI/CD GitHub Actions
- [ ] Nginx
- [ ] AWS EC2
- [ ] Cloudinary
- [ ] Elasticsearch
- [ ] RabbitMQ
- [ ] Monitoring (Prometheus + Grafana)

---

# Phase 3 (Mức Senior Portfolio)

- [ ] Microservices
- [ ] API Gateway
- [ ] Service Discovery
- [ ] Kafka
- [ ] Kubernetes
- [ ] Distributed Cache
- [ ] CDN
- [ ] Object Storage
- [ ] Rate Limiter
- [ ] Audit Log
- [ ] Event Driven Architecture
- [ ] Clean Architecture
- [ ] Unit Test
- [ ] Integration Test
- [ ] Load Test
- [ ] Security Test

---

# Mục tiêu cuối cùng

Sau khi hoàn thành toàn bộ các Phase, Desmos sẽ không chỉ là một đồ án tốt nghiệp mà còn là một **portfolio project** có giá trị để đưa vào CV. Dự án sẽ thể hiện năng lực xây dựng một hệ thống mạng xã hội full-stack với React, Spring Boot, JWT, WebSocket, Redis, Docker và khả năng mở rộng theo kiến trúc hiện đại.