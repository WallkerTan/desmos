package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.React;
import com.example.backend.model.enums.Reaction;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {

    // ==================== MODULE 8: REACTION ====================

    // Tìm react của user trên 1 bài viết (kiểm tra đã react chưa)
    Optional<React> findByUserIdAndPostId(Long userId, Long postId);

    // Tìm react của user trên 1 bình luận (kiểm tra đã react chưa)
    Optional<React> findByUserIdAndCommentId(Long userId, Long commentId);

    // Kiểm tra user đã react bài viết chưa
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // Kiểm tra user đã react bình luận chưa
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);

    // Lấy danh sách react của 1 bài viết
    List<React> findByPostId(Long postId);

    // Lấy danh sách react của 1 bình luận
    List<React> findByCommentId(Long commentId);

    // Đếm tổng react của 1 bài viết
    Long countByPostId(Long postId);

    // Đếm tổng react của 1 bình luận
    Long countByCommentId(Long commentId);

    // Đếm react theo loại cảm xúc trên 1 bài viết (ví dụ: 5 like, 3 love, 2 haha)
    @Query("SELECT r.reaction, COUNT(r) FROM React r WHERE r.post.id = :postId GROUP BY r.reaction")
    List<Object[]> countReactionsByTypeForPost(@Param("postId") Long postId);

    // Đếm react theo loại cảm xúc trên 1 bình luận
    @Query("SELECT r.reaction, COUNT(r) FROM React r WHERE r.comment.id = :commentId GROUP BY r.reaction")
    List<Object[]> countReactionsByTypeForComment(@Param("commentId") Long commentId);

    // Xóa react của user trên bài viết (Undo React)
    @Modifying
    @Query("DELETE FROM React r WHERE r.user.id = :userId AND r.post.id = :postId")
    void deleteByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    // Xóa react của user trên bình luận (Undo React)
    @Modifying
    @Query("DELETE FROM React r WHERE r.user.id = :userId AND r.comment.id = :commentId")
    void deleteByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);

    // Cập nhật loại cảm xúc (đổi react)
    @Modifying
    @Query("UPDATE React r SET r.reaction = :newReaction WHERE r.user.id = :userId AND r.post.id = :postId")
    void updateReactionForPost(@Param("userId") Long userId, @Param("postId") Long postId,
                                @Param("newReaction") Reaction newReaction);

    @Modifying
    @Query("UPDATE React r SET r.reaction = :newReaction WHERE r.user.id = :userId AND r.comment.id = :commentId")
    void updateReactionForComment(@Param("userId") Long userId, @Param("commentId") Long commentId,
                                   @Param("newReaction") Reaction newReaction);

    // Xóa tất cả react của 1 bài viết (khi xóa bài viết)
    @Modifying
    @Query("DELETE FROM React r WHERE r.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    // Xóa tất cả react của 1 bình luận (khi xóa bình luận)
    @Modifying
    @Query("DELETE FROM React r WHERE r.comment.id = :commentId")
    void deleteAllByCommentId(@Param("commentId") Long commentId);

    // Thống kê: đếm tổng lượt react trong khoảng thời gian
    @Query("SELECT COUNT(r) FROM React r WHERE r.createAt BETWEEN :startDate AND :endDate")
    Long countReactsInPeriod(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    // Lấy danh sách user đã react 1 bài viết (hiển thị ai đã like)
    @Query("SELECT r FROM React r WHERE r.post.id = :postId ORDER BY r.createAt DESC")
    List<React> findReactUsersForPost(@Param("postId") Long postId);
}
