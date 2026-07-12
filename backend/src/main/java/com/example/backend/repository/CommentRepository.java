package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ==================== MODULE 7: COMMENT ====================

    // Lấy danh sách bình luận gốc (cấp 1) của 1 bài viết (comment_id IS NULL)
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.comment IS NULL " +
           "AND c.deleteAt IS NULL ORDER BY c.createAt DESC")
    Page<Comment> findRootCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    // Lấy danh sách reply (cấp 2) của 1 bình luận cha
    @Query("SELECT c FROM Comment c WHERE c.comment.id = :parentCommentId " +
           "AND c.deleteAt IS NULL ORDER BY c.createAt ASC")
    Page<Comment> findRepliesByCommentId(@Param("parentCommentId") Long parentCommentId, Pageable pageable);

    // Lấy tất cả bình luận của 1 bài viết (bao gồm reply), dùng cho đếm
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.deleteAt IS NULL")
    List<Comment> findAllByPostId(@Param("postId") Long postId);

    // Đếm số lượng bình luận gốc của 1 bài viết
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId " +
           "AND c.comment IS NULL AND c.deleteAt IS NULL")
    Long countRootCommentsByPostId(@Param("postId") Long postId);

    // Đếm tổng số bình luận (cả reply) của 1 bài viết
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId AND c.deleteAt IS NULL")
    Long countAllCommentsByPostId(@Param("postId") Long postId);

    // Đếm số reply của 1 bình luận
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.comment.id = :commentId AND c.deleteAt IS NULL")
    Long countRepliesByCommentId(@Param("commentId") Long commentId);

    // Xóa mềm bình luận (chủ bình luận xóa)
    @Modifying
    @Query("UPDATE Comment c SET c.deleteAt = CURRENT_TIMESTAMP WHERE c.id = :commentId AND c.user.id = :userId")
    void softDeleteByOwner(@Param("commentId") Long commentId, @Param("userId") Long userId);

    // Xóa mềm bình luận (chủ bài viết xóa bình luận trong bài của mình)
    @Modifying
    @Query("UPDATE Comment c SET c.deleteAt = CURRENT_TIMESTAMP WHERE c.id = :commentId AND c.post.user.id = :postOwnerId")
    void softDeleteByPostOwner(@Param("commentId") Long commentId, @Param("postOwnerId") Long postOwnerId);

    // Xóa mềm tất cả reply khi bình luận cha bị xóa
    @Modifying
    @Query("UPDATE Comment c SET c.deleteAt = CURRENT_TIMESTAMP WHERE c.comment.id = :parentCommentId")
    void softDeleteRepliesByParentId(@Param("parentCommentId") Long parentCommentId);

    // Lấy danh sách bình luận của 1 user (hiển thị trên profile hoặc admin)
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.deleteAt IS NULL " +
           "ORDER BY c.createAt DESC")
    Page<Comment> findByUserId(@Param("userId") Long userId, Pageable pageable);

    // Thống kê: đếm tổng bình luận trong khoảng thời gian
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.createAt BETWEEN :startDate AND :endDate " +
           "AND c.deleteAt IS NULL")
    Long countCommentsInPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
