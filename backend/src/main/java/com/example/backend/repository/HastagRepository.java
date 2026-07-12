package com.example.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.Hastag;

@Repository
public interface HastagRepository extends JpaRepository<Hastag, Long> {

    // ==================== MODULE 5: HASHTAG ====================

    // Lấy danh sách hashtag của 1 bài viết
    List<Hastag> findByPostId(Long postId);

    // Xóa tất cả hashtag của 1 bài viết (khi sửa bài viết, xóa cũ thêm mới)
    @Modifying
    @Query("DELETE FROM Hastag h WHERE h.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    // ==================== MODULE 10: SEARCH HASHTAG ====================

    // Tìm bài viết theo hashtag (tên hashtag chứa từ khóa)
    @Query("SELECT h FROM Hastag h WHERE LOWER(h.type) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "AND h.post.deleteAt IS NULL AND h.post.status = com.example.backend.model.enums.PostStatus.published " +
           "ORDER BY h.post.       createAt DESC")
    Page<Hastag> searchByHashtagKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Tìm bài viết theo hashtag chính xác
    @Query("SELECT h FROM Hastag h WHERE LOWER(h.type) = LOWER(:hashtagName) " +
           "AND h.post.deleteAt IS NULL AND h.post.status = com.example.backend.model.enums.PostStatus.published " +
           "ORDER BY h.post.createAt DESC")
    Page<Hastag> findByExactHashtag(@Param("hashtagName") String hashtagName, Pageable pageable);

    // Lấy danh sách hashtag phổ biến (trending) - đếm số bài viết dùng hashtag
    @Query("SELECT h.type, COUNT(h) as cnt FROM Hastag h " +
           "WHERE h.post.deleteAt IS NULL AND h.post.status = com.example.backend.model.enums.PostStatus.published " +
           "GROUP BY h.type ORDER BY cnt DESC")
    List<Object[]> findTrendingHashtags(Pageable pageable);

    // Kiểm tra hashtag có tồn tại trong bài viết không
    boolean existsByTypeAndPostId(String type, Long postId);

    // Đếm số bài viết sử dụng 1 hashtag
    @Query("SELECT COUNT(DISTINCT h.post.id) FROM Hastag h WHERE LOWER(h.type) = LOWER(:hashtagName) " +
           "AND h.post.deleteAt IS NULL AND h.post.status = com.example.backend.model.enums.PostStatus.published")
    Long countPostsByHashtag(@Param("hashtagName") String hashtagName);
}
