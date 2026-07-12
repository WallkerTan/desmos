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

import com.example.backend.model.entity.Post;
import com.example.backend.model.entity.User;
import com.example.backend.model.enums.PostStatus;
import com.example.backend.model.enums.Privacy;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

       // ==================== MODULE 5: POST - CRUD ====================

       // Lấy danh sách bài viết của 1 user (trang cá nhân), chưa bị xóa
       @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.status <> com.example.backend.model.enums.PostStatus.deleted "
                     + "AND p.deleteAt IS NULL ORDER BY p.createAt DESC")
       Page<Post> findByUserIdAndNotDeleted(@Param("userId") Long userId, Pageable pageable);

       // Lấy bài viết công khai của 1 user (người khác xem trang cá nhân)
       @Query("SELECT p FROM Post p WHERE p.user.id = :userId "
                     + "AND p.privacy = com.example.backend.model.enums.Privacy.globle "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND p.deleteAt IS NULL ORDER BY p.createAt DESC")
       Page<Post> findPublicPostsByUserId(@Param("userId") Long userId, Pageable pageable);

       // Lấy bài viết bạn bè có quyền xem (public + friends) của 1 user
       @Query("SELECT p FROM Post p WHERE p.user.id = :userId "
                     + "AND p.privacy IN (com.example.backend.model.enums.Privacy.globle, com.example.backend.model.enums.Privacy.friends) "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND p.deleteAt IS NULL ORDER BY p.createAt DESC")
       Page<Post> findFriendVisiblePostsByUserId(@Param("userId") Long userId, Pageable pageable);

       // ==================== MODULE 5: NEWS FEED ====================

       // Bảng tin: lấy bài viết từ danh sách người theo dõi (following) - bài công khai
       @Query("SELECT p FROM Post p WHERE p.user.id IN :followingIds "
                     + "AND p.privacy = com.example.backend.model.enums.Privacy.globle "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND p.deleteAt IS NULL ORDER BY p.createAt DESC")
       Page<Post> findNewsFeedPublicPosts(@Param("followingIds") List<Long> followingIds,
                     Pageable pageable);

       // Bảng tin: lấy bài viết từ bạn bè (chế độ friends + globle)
       @Query("SELECT p FROM Post p WHERE p.user.id IN :friendIds "
                     + "AND p.privacy IN (com.example.backend.model.enums.Privacy.globle, com.example.backend.model.enums.Privacy.friends) "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND p.deleteAt IS NULL ORDER BY p.createAt DESC")
       Page<Post> findNewsFeedFriendPosts(@Param("friendIds") List<Long> friendIds,
                     Pageable pageable);

       // Bảng tin tổng hợp: lấy bài viết từ following + friends (loại bỏ trùng lặp)
       @Query("SELECT DISTINCT p FROM Post p WHERE p.deleteAt IS NULL "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND ("
                     + "  (p.user.id IN :followingIds AND p.privacy = com.example.backend.model.enums.Privacy.globle) "
                     + "  OR (p.user.id IN :friendIds AND p.privacy IN (com.example.backend.model.enums.Privacy.globle, com.example.backend.model.enums.Privacy.friends))"
                     + ") ORDER BY p.createAt DESC")
       Page<Post> findCombinedNewsFeed(@Param("followingIds") List<Long> followingIds,
                     @Param("friendIds") List<Long> friendIds, Pageable pageable);

       // ==================== MODULE 5: XÓA MỀM ====================

       // Xóa mềm bài viết
       @Modifying
       @Query("UPDATE Post p SET p.status = com.example.backend.model.enums.PostStatus.deleted, "
                     + "p.deleteAt = CURRENT_TIMESTAMP WHERE p.id = :postId AND p.user.id = :userId")
       void softDeletePost(@Param("postId") Long postId, @Param("userId") Long userId);

       // ==================== MODULE 10: SEARCH ====================

       // Tìm kiếm bài viết công khai theo từ khóa trong caption
       @Query("SELECT p FROM Post p WHERE p.deleteAt IS NULL "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published "
                     + "AND p.privacy = com.example.backend.model.enums.Privacy.globle "
                     + "AND LOWER(p.caption) LIKE LOWER(CONCAT('%', :keyword, '%')) "
                     + "ORDER BY p.createAt DESC")
       Page<Post> searchByCaption(@Param("keyword") String keyword, Pageable pageable);

       // ==================== MODULE 12: ADMIN ====================

       // Admin: lấy tất cả bài viết (bao gồm đã xóa)
       @Query("SELECT p FROM Post p ORDER BY p.createAt DESC")
       Page<Post> findAllForAdmin(Pageable pageable);

       // Admin: lọc bài viết theo trạng thái
       Page<Post> findByStatus(PostStatus status, Pageable pageable);

       // Admin: xóa mềm bài viết (không cần kiểm tra quyền sở hữu)
       @Modifying
       @Query("UPDATE Post p SET p.status = com.example.backend.model.enums.PostStatus.deleted, "
                     + "p.deleteAt = CURRENT_TIMESTAMP WHERE p.id = :postId")
       void adminSoftDeletePost(@Param("postId") Long postId);

       // Thống kê: đếm tổng bài viết trong khoảng thời gian
       @Query("SELECT COUNT(p) FROM Post p WHERE p.createAt BETWEEN :startDate AND :endDate "
                     + "AND p.status <> com.example.backend.model.enums.PostStatus.deleted")
       Long countPostsInPeriod(@Param("startDate") LocalDateTime startDate,
                     @Param("endDate") LocalDateTime endDate);

       // Đếm tổng bài viết của 1 user (hiển thị trên profile)
       @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId "
                     + "AND p.status = com.example.backend.model.enums.PostStatus.published AND p.deleteAt IS NULL")
       Long countByUserId(@Param("userId") Long userId);

       // Lấy bài viết theo privacy (dùng cho filter)
       Page<Post> findByUserAndPrivacyAndStatusAndDeleteAtIsNull(User user, Privacy privacy,
                     PostStatus status, Pageable pageable);

}
