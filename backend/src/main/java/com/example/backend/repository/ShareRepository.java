package com.example.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Share;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    // ==================== MODULE 5: SHARE ====================

    // Lấy danh sách bài viết mà 1 user đã chia sẻ
    @Query("SELECT s FROM Share s WHERE s.user.id = :userId ORDER BY s.createAt DESC")
    Page<Share> findByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách chia sẻ của 1 bài viết gốc
    @Query("SELECT s FROM Share s WHERE s.post.id = :postId ORDER BY s.createAt DESC")
    Page<Share> findByPostId(@Param("postId") Long postId, Pageable pageable);

    // Đếm số lượt chia sẻ của 1 bài viết
    Long countByPostId(Long postId);

    // Kiểm tra user đã chia sẻ bài viết này chưa
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // Xóa bài chia sẻ (chủ sở hữu share)
    void deleteByIdAndUserId(Long id, Long userId);
}
