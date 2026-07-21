package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.User;
import com.example.backend.model.enums.Role;

@Repository
public interface UserReposetory extends JpaRepository<User, Long> {

    // ==================== MODULE 1: AUTHENTICATION ====================

    // Đăng nhập: tìm user bằng email
    Optional<User> findByEmail(String email);

    // Đăng nhập: tìm user bằng username
    Optional<User> findByUsername(String username);

    // Đăng nhập: tìm bằng email hoặc username (hỗ trợ đăng nhập linh hoạt)
    Optional<User> findByEmailOrUsername(String email, String username);

    // Đăng ký: kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);

    // Đăng ký: kiểm tra username đã tồn tại chưa
    boolean existsByUsername(String username);

    // Khóa tài khoản: cập nhật số lần đăng nhập sai & thời gian khóa
    @Modifying
    @Query("UPDATE User u SET u.falseLogin = :falseLogin, u.lockUntil = :lockUntil WHERE u.id = :userId")
    void updateLoginAttempts(@Param("userId") Long userId,
                             @Param("falseLogin") Integer falseLogin,
                             @Param("lockUntil") java.sql.Timestamp lockUntil);

    // Reset số lần đăng nhập sai khi đăng nhập thành công
    @Modifying
    @Query("UPDATE User u SET u.falseLogin = 0, u.lockUntil = null WHERE u.id = :userId")
    void resetLoginAttempts(@Param("userId") Long userId);

    // Xác thực email: cập nhật trạng thái is_verified
    @Modifying
    @Query("UPDATE User u SET u.isVerified = true WHERE u.id = :userId")
    void verifyEmail(@Param("userId") Long userId);

    // Quên mật khẩu: cập nhật mật khẩu mới theo email
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);

    // Đổi mật khẩu: cập nhật mật khẩu mới theo userId
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    void updatePasswordById(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    // ==================== MODULE 2: USER PROFILE ====================

    // Xem hồ sơ cá nhân theo username
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deleteAt IS NULL AND u.isActive = true")
    Optional<User> findActiveByUsername(@Param("username") String username);

    // Cập nhật avatar
    @Modifying
    @Query("UPDATE User u SET u.avata = :avatarUrl WHERE u.id = :userId")
    void updateAvatar(@Param("userId") Long userId, @Param("avatarUrl") String avatarUrl);

    // Cập nhật ảnh bìa
    @Modifying
    @Query("UPDATE User u SET u.coverPhoto = :coverUrl WHERE u.id = :userId")
    void updateCoverPhoto(@Param("userId") Long userId, @Param("coverUrl") String coverUrl);

    // ==================== MODULE 3: FRIENDSHIP (hỗ trợ) ====================

    // Lấy danh sách bạn bè của 1 user (user nằm trong cặp user1 hoặc user2 với status = friend)
    @Query("SELECT CASE WHEN f.user1.id = :userId THEN f.user2 ELSE f.user1 END " +
           "FROM Friend f WHERE (f.user1.id = :userId OR f.user2.id = :userId) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL")
    Page<User> findFriendsByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách lời mời kết bạn đang chờ (người nhận)
    @Query("SELECT fs.sender FROM Friendship fs WHERE fs.receiver.id = :userId")
    Page<User> findPendingFriendRequests(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách lời mời kết bạn đã gửi (người gửi)
    @Query("SELECT fs.receiver FROM Friendship fs WHERE fs.sender.id = :userId")
    Page<User> findSentFriendRequests(@Param("userId") Long userId, Pageable pageable);

    // ==================== MODULE 4: FOLLOW (hỗ trợ) ====================

    // Lấy danh sách người đang theo dõi user (followers)
    @Query("SELECT f.follower FROM Follow f WHERE f.following.id = :userId")
    Page<User> findFollowersByUserId(@Param("userId") Long userId, Pageable pageable);

    // Lấy danh sách user đang theo dõi (following)
    @Query("SELECT f.following FROM Follow f WHERE f.follower.id = :userId")
    Page<User> findFollowingByUserId(@Param("userId") Long userId, Pageable pageable);

    // ==================== MODULE 10: SEARCH ====================

    // Tìm kiếm người dùng theo từ khóa (username hoặc email, partial match)
    @Query("SELECT u FROM User u WHERE u.deleteAt IS NULL AND u.isActive = true " +
           "AND (LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<User> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // ==================== MODULE 12: ADMIN ====================

    // Lấy tất cả user (bao gồm cả bị xóa mềm) cho admin quản lý
    Page<User> findAll(Pageable pageable);

    // Lọc user theo trạng thái hoạt động
    Page<User> findByIsActive(Boolean isActive, Pageable pageable);

    // Lọc user theo role
    Page<User> findByRole(Role role, Pageable pageable);

    // Admin: khóa tài khoản người dùng
    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :userId")
    void updateActiveStatus(@Param("userId") Long userId, @Param("isActive") Boolean isActive);

    // Thống kê: đếm tổng user đăng ký mới trong khoảng thời gian
    @Query("SELECT COUNT(u) FROM User u WHERE u.createAt BETWEEN :startDate AND :endDate")
    Long countNewUsersInPeriod(@Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate);

    // Thống kê: đếm tổng user đang hoạt động
    Long countByIsActiveTrue();

    // Tìm danh sách user theo list IDs (dùng nội bộ cho nhiều module)
    List<User> findByIdIn(List<Long> ids);

    // Gợi ý kết bạn: tìm người dùng chưa phải bạn bè (loại trừ danh sách ID)
    @Query("SELECT u FROM User u WHERE u.id NOT IN :excludeIds " +
           "AND u.deleteAt IS NULL AND u.isActive = true AND u.id <> :userId")
    Page<User> findSuggestedFriends(@Param("userId") Long userId,
                                     @Param("excludeIds") List<Long> excludeIds,
                                     Pageable pageable);
}
