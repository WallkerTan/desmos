package com.example.backend.service.impl;

import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.User;
import com.example.backend.model.entity.Hastag;
import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.request.PostRequest;
import com.example.backend.model.mapper.PostMapper;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserReposetory;
import com.example.backend.repository.HastagRepository;
import com.example.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserReposetory userRepository;
    private final HastagRepository hastagRepository;
    private final PostMapper postMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User is not authenticated");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // Lấy bài viết theo ID (bỏ qua bài đã xóa)
    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .filter(p -> p.getDeleteAt() == null && p.getStatus() != PostStatus.deleted)
                .orElseThrow(() -> new RuntimeException("Post not found or has been deleted with id: " + id));
    }

    // Tìm kiếm bài viết theo nội dung caption (phân trang)
    @Override
    public Page<Post> findByCaption(String caption, Pageable pageable) {
        return postRepository.searchByCaption(caption, pageable);
    }

    // Đăng bài viết mới
    @Override
    @Transactional
    public Post createPost(PostRequest postRequest) {
        if (postRequest == null) {
            throw new IllegalArgumentException("PostRequest cannot be null");
        }
        User currentUser = getCurrentUser();
        Post post = postMapper.toEntity(postRequest);
        post.setUser(currentUser);
        post.setCreateAt(LocalDateTime.now());
        
        Post savedPost = postRepository.save(post);
        saveHashtags(savedPost);
        return savedPost;
    }

    // Chỉnh sửa bài viết
    @Override
    @Transactional
    public Boolean updatePost(Long id, PostRequest postRequest) {
        if (postRequest == null) {
            throw new IllegalArgumentException("PostRequest cannot be null");
        }
        User currentUser = getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        
        // Ensure ownership
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to update this post");
        }

        post.setCaption(postRequest.getCaption());
        post.setPrivacy(postRequest.getPrivacy());
        post.setStatus(postRequest.getStatus());
        post.setUpdateAt(LocalDateTime.now());

        postRepository.save(post);
        saveHashtags(post);
        return true;
    }

    // Xóa mềm bài viết
    @Override
    @Transactional
    public Boolean deletePost(long id) {
        User currentUser = getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        // Ensure ownership
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to delete this post");
        }

        postRepository.softDeletePost(id, currentUser.getId());
        return true;
    }

    private void saveHashtags(Post post) {
        hastagRepository.deleteAllByPostId(post.getId());
        String caption = post.getCaption();
        if (caption != null && !caption.isEmpty()) {
            Pattern pattern = Pattern.compile("#(\\w+)");
            Matcher matcher = pattern.matcher(caption);
            while (matcher.find()) {
                String hashtagName = matcher.group(1);
                if (!hastagRepository.existsByTypeAndPostId(hashtagName, post.getId())) {
                    Hastag hashtag = Hastag.builder()
                            .type(hashtagName)
                            .post(post)
                            .build();
                    hastagRepository.save(hashtag);
                }
            }
        }
    }
}
