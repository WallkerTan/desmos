package com.example.backend.service.impl;

import com.example.backend.model.entity.Notification;
import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.Share;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.mapper.ShareMapper;
import com.example.backend.model.request.ShareRequest;
import com.example.backend.model.respon.ShareResponse;
import com.example.backend.repository.NotificationRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.ShareRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.service.ShareService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final UserReposetory userRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final ShareMapper shareMapper;

    // Chia sẻ bài viết
    @Override
    @Transactional
    public ShareResponse sharePost(Long userId, ShareRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + userId));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với id: " + request.getPostId()));

        // Kiểm tra bài viết chưa bị xóa
        if (post.getDeleteAt() != null || post.getStatus() == PostStatus.deleted) {
            throw new RuntimeException("Bài viết đã bị xóa, không thể chia sẻ");
        }

        // Tạo bản ghi chia sẻ
        Share share = Share.builder()
                .caption(request.getCaption())
                .user(user)
                .post(post)
                .createAt(LocalDateTime.now())
                .build();
        Share savedShare = shareRepository.save(share);

        // Gửi thông báo cho chủ bài viết (nếu không phải chính mình chia sẻ)
        if (!post.getUser().getId().equals(userId)) {
            Notification notification = Notification.builder()
                    .user(post.getUser())
                    .type(NotificationType.POST_SHARE)
                    .message(user.getUsername() + " đã chia sẻ bài viết của bạn")
                    .targetId(post.getId())
                    .action("/posts/" + post.getId())
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
        }

        return shareMapper.toResponse(savedShare);
    }

    // Xóa bài chia sẻ (chỉ chủ sở hữu mới được xóa)
    @Override
    @Transactional
    public Boolean deleteShare(Long userId, Long shareId) {
        Share share = shareRepository.findById(shareId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài chia sẻ với id: " + shareId));

        // Kiểm tra quyền xóa (chỉ chủ sở hữu bài chia sẻ)
        if (!share.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền xóa bài chia sẻ này");
        }

        shareRepository.deleteById(shareId);
        return true;
    }
}
