package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // ==================== MODULE 4: FOLLOW ====================

    // Kiểm tra A có đang theo dõi B không
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // Tìm bản ghi follow cụ thể
    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // Bỏ theo dõi (xóa bản ghi)
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
    void deleteByFollowerIdAndFollowingId(@Param("followerId") Long followerId,
                                          @Param("followingId") Long followingId);

    // Lấy danh sách người theo dõi user (followers) - phân trang
    @Query("SELECT f FROM Follow f WHERE f.following.id = :userId ORDER BY f.createAt DESC")
    Page<Follow> findFollowersByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách user đang theo dõi (following) - phân trang
    @Query("SELECT f FROM Follow f WHERE f.follower.id = :userId ORDER BY f.createAt DESC")
    Page<Follow> findFollowingByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách ID người đang theo dõi (dùng cho News Feed)
    @Query("SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId")
    List<Long> findFollowingIds(@Param("userId") Long userId);

    // Lấy danh sách ID followers
    @Query("SELECT f.follower.id FROM Follow f WHERE f.following.id = :userId")
    List<Long> findFollowerIds(@Param("userId") Long userId);

    // Đếm số lượng followers
    Long countByFollowingId(Long followingId);

    // Đếm số lượng following
    Long countByFollowerId(Long followerId);

    // Tự động follow khi kết bạn: thêm follow 2 chiều đã được xử lý ở service
    // Nhưng cần kiểm tra đã follow chưa trước khi thêm -> dùng existsByFollowerIdAndFollowingId

    // Kiểm tra theo dõi lẫn nhau (mutual follow)
    @Query("SELECT CASE WHEN COUNT(f) = 2 THEN true ELSE false END FROM Follow f " +
           "WHERE (f.follower.id = :userId1 AND f.following.id = :userId2) " +
           "OR (f.follower.id = :userId2 AND f.following.id = :userId1)")
    boolean isMutualFollow(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
