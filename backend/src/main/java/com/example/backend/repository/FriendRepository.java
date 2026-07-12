package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Friend;
import com.example.backend.model.enums.FriendStatus;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    // ==================== MODULE 3: FRIENDSHIP ====================

    // Tìm mối quan hệ giữa 2 user (bất kể ai là user1, user2)
    @Query("SELECT f FROM Friend f WHERE " +
           "((f.user1.id = :userId1 AND f.user2.id = :userId2) " +
           "OR (f.user1.id = :userId2 AND f.user2.id = :userId1)) " +
           "AND f.deleteAt IS NULL")
    Optional<Friend> findRelationship(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Kiểm tra 2 user đã là bạn bè chưa
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friend f WHERE " +
           "((f.user1.id = :userId1 AND f.user2.id = :userId2) " +
           "OR (f.user1.id = :userId2 AND f.user2.id = :userId1)) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL")
    boolean isFriend(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Kiểm tra user A có bị user B chặn không
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friend f WHERE " +
           "f.user1.id = :blockerId AND f.user2.id = :blockedId " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.block " +
           "AND f.deleteAt IS NULL")
    boolean isBlocked(@Param("blockerId") Long blockerId, @Param("blockedId") Long blockedId);

    // Kiểm tra có bất kỳ mối quan hệ chặn nào giữa 2 user không (A chặn B hoặc B chặn A)
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friend f WHERE " +
           "((f.user1.id = :userId1 AND f.user2.id = :userId2) " +
           "OR (f.user1.id = :userId2 AND f.user2.id = :userId1)) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.block " +
           "AND f.deleteAt IS NULL")
    boolean isAnyBlocked(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Lấy danh sách bạn bè của 1 user
    @Query("SELECT f FROM Friend f WHERE (f.user1.id = :userId OR f.user2.id = :userId) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL")
    List<Friend> findAllFriends(@Param("userId") Long userId);

    // Lấy danh sách ID bạn bè (dùng cho News Feed query)
    @Query("SELECT CASE WHEN f.user1.id = :userId THEN f.user2.id ELSE f.user1.id END " +
           "FROM Friend f WHERE (f.user1.id = :userId OR f.user2.id = :userId) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL")
    List<Long> findFriendIds(@Param("userId") Long userId);

    // Đếm số lượng bạn bè của 1 user
    @Query("SELECT COUNT(f) FROM Friend f WHERE (f.user1.id = :userId OR f.user2.id = :userId) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL")
    Long countFriends(@Param("userId") Long userId);

    // Cập nhật trạng thái mối quan hệ (accept, block, unfriend)
    @Modifying
    @Query("UPDATE Friend f SET f.status = :status WHERE f.id = :friendId")
    void updateStatus(@Param("friendId") Long friendId, @Param("status") FriendStatus status);

    // Xóa mềm mối quan hệ (unfriend)
    @Modifying
    @Query("UPDATE Friend f SET f.status = com.example.backend.model.enums.FriendStatus.none, " +
           "f.deleteAt = CURRENT_TIMESTAMP WHERE f.id = :friendId")
    void softDelete(@Param("friendId") Long friendId);

    // Lấy danh sách user bị chặn bởi 1 user
    @Query("SELECT f FROM Friend f WHERE f.user1.id = :userId " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.block " +
           "AND f.deleteAt IS NULL")
    List<Friend> findBlockedByUser(@Param("userId") Long userId);

    // Lấy danh sách ID user bị chặn (dùng cho filter)
    @Query("SELECT f.user2.id FROM Friend f WHERE f.user1.id = :userId " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.block " +
           "AND f.deleteAt IS NULL")
    List<Long> findBlockedUserIds(@Param("userId") Long userId);

    // Lấy danh sách bạn chung giữa 2 user
    @Query("SELECT CASE WHEN f.user1.id = :userId1 THEN f.user2.id ELSE f.user1.id END " +
           "FROM Friend f WHERE (f.user1.id = :userId1 OR f.user2.id = :userId1) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f.deleteAt IS NULL " +
           "AND CASE WHEN f.user1.id = :userId1 THEN f.user2.id ELSE f.user1.id END IN " +
           "(SELECT CASE WHEN f2.user1.id = :userId2 THEN f2.user2.id ELSE f2.user1.id END " +
           "FROM Friend f2 WHERE (f2.user1.id = :userId2 OR f2.user2.id = :userId2) " +
           "AND f2.status = com.example.backend.model.enums.FriendStatus.friend " +
           "AND f2.deleteAt IS NULL)")
    List<Long> findMutualFriendIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    // Đếm số bạn chung
    @Query("SELECT COUNT(mutualId) FROM (" +
           "SELECT CASE WHEN f.user1.id = :userId1 THEN f.user2.id ELSE f.user1.id END AS mutualId " +
           "FROM Friend f WHERE (f.user1.id = :userId1 OR f.user2.id = :userId1) " +
           "AND f.status = com.example.backend.model.enums.FriendStatus.friend AND f.deleteAt IS NULL " +
           "AND CASE WHEN f.user1.id = :userId1 THEN f.user2.id ELSE f.user1.id END IN " +
           "(SELECT CASE WHEN f2.user1.id = :userId2 THEN f2.user2.id ELSE f2.user1.id END " +
           "FROM Friend f2 WHERE (f2.user1.id = :userId2 OR f2.user2.id = :userId2) " +
           "AND f2.status = com.example.backend.model.enums.FriendStatus.friend AND f2.deleteAt IS NULL))")
    Long countMutualFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
