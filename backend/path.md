# 1. Auth API (`/api/v1/auth`)

URL /api/v1/auth/register

POST

Chức năng Đăng ký tài khoản mới

Body Body: `Account` (username, email, password...)

---

URL /api/v1/auth/login

POST

Chức năng Đăng nhập

Body Body: `LoginRequest` (email, password)

---

URL /api/v1/auth/logout

POST

Chức năng Đăng xuất

Body Không

---

URL /api/v1/auth/forgot-password

POST

Chức năng Quên mật khẩu

Body Param: `email`

---

URL /api/v1/auth/reset-password

POST

Chức năng Đặt lại mật khẩu

Body Param: `email`, `newPassword`

---

# 2. User API (`/api/v1/users`)

URL /api/v1/users/me/password

PUT

Chức năng Đổi mật khẩu

Body Param: `currentUserId`, `newPassword`

---

URL /api/v1/users/me

PUT

Chức năng Cập nhật hồ sơ cá nhân

Body Param: `currentUserId`, Body: `UserRequest`

---

URL /api/v1/users/search

GET

Chức năng Tìm kiếm người dùng

Body Param: `username`

---

# 3. Friendship API (`/api/v1/friends`)

URL /api/v1/friends

GET

Chức năng Lấy danh sách bạn bè

Body Param: `currentUserId`

---

URL /api/v1/friends/requests/{receiverId}

POST

Chức năng Gửi lời mời kết bạn

Body Param: `currentUserId`

---

URL /api/v1/friends/requests/{senderId}/accept

POST

Chức năng Chấp nhận lời mời kết bạn

Body Param: `currentUserId`

---

URL /api/v1/friends/requests/{senderId}/reject

POST

Chức năng Từ chối lời mời kết bạn

Body Param: `currentUserId`

---

URL /api/v1/friends/requests/{receiverId}

DELETE

Chức năng Hủy bỏ lời mời đã gửi

Body Param: `currentUserId`

---

URL /api/v1/friends/{friendId}

DELETE

Chức năng Hủy kết bạn (Unfriend)

Body Param: `currentUserId`

---

URL /api/v1/friends/{blockedId}/block

POST

Chức năng Chặn người dùng

Body Param: `currentUserId`

---

URL /api/v1/friends/{blockedId}/unblock

POST

Chức năng Bỏ chặn người dùng

Body Param: `currentUserId`

---

URL /api/v1/friends/requests/pending

GET

Chức năng Lấy danh sách lời mời chờ duyệt

Body Param: `currentUserId`, `page`, `size`

---

URL /api/v1/friends/blocked

GET

Chức năng Lấy danh sách đã chặn

Body Param: `currentUserId`

---

# 4. Follow API (`/api/v1/follows`)

URL /api/v1/follows/{followingId}

POST

Chức năng Bắt đầu theo dõi ai đó

Body Param: `currentUserId`

---

URL /api/v1/follows/{followingId}

DELETE

Chức năng Bỏ theo dõi

Body Param: `currentUserId`

---

URL /api/v1/follows/followers

GET

Chức năng Lấy danh sách người theo dõi mình

Body Param: `currentUserId`, `page`, `size`

---

URL /api/v1/follows/following

GET

Chức năng Lấy danh sách người mình đang theo dõi

Body Param: `currentUserId`, `page`, `size`

---

# 5. React API (`/api/v1/reacts`)

URL /api/v1/reacts

POST

Chức năng Thả/Hủy cảm xúc bài viết/comment

Body Param: `currentUserId`, Body: `ReactRequest`

---

URL /api/v1/reacts/posts/{postId}

GET

Chức năng Lấy danh sách cảm xúc của 1 bài viết

Body Không

---

URL /api/v1/reacts/comments/{commentId}

GET

Chức năng Lấy danh sách cảm xúc của 1 bình luận

Body Không

---

# 6. Comment API (`/api/v1/comments`)

URL /api/v1/comments

POST

Chức năng Viết bình luận mới / Trả lời

Body Param: `currentUserId`, Body: `CommentRequest`

---

URL /api/v1/comments/{commentId}

PUT

Chức năng Sửa bình luận

Body Param: `currentUserId`, Body: `String content`

---

URL /api/v1/comments/{commentId}

DELETE

Chức năng Xóa bình luận

Body Param: `currentUserId`

---

URL /api/v1/comments/posts/{postId}

GET

Chức năng Lấy danh sách bình luận gốc của bài

Body Param: `page`, `size`

---

URL /api/v1/comments/{commentId}/replies

GET

Chức năng Lấy danh sách phản hồi (replies)

Body Param: `page`, `size`

---

# 7. Post API (`/api/v1/posts`)

URL /api/v1/posts

POST

Chức năng Đăng bài viết mới

Body Body: `PostRequest`

---

URL /api/v1/posts/{id}

GET

Chức năng Lấy bài viết theo ID

Body Không

---

URL /api/v1/posts/search

GET

Chức năng Tìm kiếm bài viết theo nội dung

Body Param: `caption`, `page`, `size`

---

URL /api/v1/posts/{id}

PUT

Chức năng Cập nhật bài viết

Body Body: `PostRequest`

---

URL /api/v1/posts/{id}

DELETE

Chức năng Xóa bài viết

Body Không

---

# 8. Post Media API (`/api/v1/post-medias`)

URL /api/v1/post-medias

POST

Chức năng Thêm tệp đính kèm

Body Body: `PostMediaRequest`

---

URL /api/v1/post-medias/posts/{postId}

GET

Chức năng Lấy danh sách tệp của bài viết

Body Không

---

URL /api/v1/post-medias/{id}

DELETE

Chức năng Xóa tệp

Body Không

---

URL /api/v1/post-medias/{id}/download

GET

Chức năng Tải xuống tệp

Body Không

---

# 9. Message API (`/api/v1/messages`)

URL /api/v1/messages/conversations/private/{userId2}

POST

Chức năng Lấy hoặc tạo nhóm chat 1-1

Body Param: `currentUserId`

---

URL /api/v1/messages/conversations/group

POST

Chức năng Tạo nhóm chat nhiều người

Body Param: `currentUserId`, `name`, Body: `List<Long> memberIds`

---

URL /api/v1/messages

POST

Chức năng Gửi tin nhắn

Body Param: `currentUserId`, `conversationId`, `content`, `mediaUrl`,
`emoji`

---

URL /api/v1/messages/{messageId}/recall

DELETE

Chức năng Thu hồi tin nhắn

Body Param: `currentUserId`

---

URL /api/v1/messages/conversations/{conversationId}

GET

Chức năng Xem tin nhắn trong nhóm

Body Param: `currentUserId`, `page`, `size`

---

URL /api/v1/messages/conversations/{conversationId}/seen

PUT

Chức năng Đánh dấu đã xem

Body Param: `currentUserId`

---

# 10. Notification API (`/api/v1/notifications`)

URL /api/v1/notifications

GET

Chức năng Lấy danh sách thông báo

Body Param: `currentUserId`, `page`, `size`

---

URL /api/v1/notifications/{notificationId}/read

PUT

Chức năng Đánh dấu 1 thông báo đã đọc

Body Param: `currentUserId`

---

URL /api/v1/notifications/read-all

PUT

Chức năng Đánh dấu tất cả đã đọc

Body Param: `currentUserId`

---

# 11. Share API (`/api/v1/shares`)

URL /api/v1/shares

POST

Chức năng Chia sẻ bài viết

Body Param: `currentUserId`, Body: `ShareRequest`

---

URL /api/v1/shares/{shareId}

DELETE

Chức năng Xóa chia sẻ

Body Param: `currentUserId`

---

# 12. Admin API (`/api/v1/admin`)

URL /api/v1/admin/users

GET

Chức năng Lấy tất cả user

Body Param: `page`, `size`

---

URL /api/v1/admin/users/filter/active

GET

Chức năng Lọc user theo trạng thái

Body Param: `isActive`, `page`, `size`

---

URL /api/v1/admin/users/filter/role

GET

Chức năng Lọc user theo quyền

Body Param: `role`, `page`, `size`

---

URL /api/v1/admin/users/{userId}/status

PUT

Chức năng Khóa / Mở khóa user

Body Param: `isActive`

---

URL /api/v1/admin/posts

GET

Chức năng Lấy tất cả bài viết

Body Param: `page`, `size`

---

URL /api/v1/admin/posts/filter/status

GET

Chức năng Lọc bài viết theo trạng thái

Body Param: `status`, `page`, `size`

---

URL /api/v1/admin/posts/{postId}

DELETE

Chức năng Admin xóa bài viết

Body Không

---

URL /api/v1/admin/statistics

GET

Chức năng Thống kê số lượng (Tổng quan)

Body Param: `startDate`, `endDate` (ISO Date Time)

---
