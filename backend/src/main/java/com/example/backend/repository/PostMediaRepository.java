package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.model.entity.PostMedia;
import com.example.backend.model.enums.MediaType;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    // ==================== MODULE 6: POST MEDIA ====================

    // Lấy danh sách media của 1 bài viết
    List<PostMedia> findByPostId(Long postId);

    // Lấy danh sách media theo loại (image/video) của 1 bài viết
    List<PostMedia> findByPostIdAndMediaType(Long postId, MediaType mediaType);

    // Đếm số lượng media trong 1 bài viết
    Long countByPostId(Long postId);

    // Xóa tất cả media của 1 bài viết (khi xóa bài viết)
    @Modifying
    @Query("DELETE FROM PostMedia pm WHERE pm.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    // Xóa 1 media cụ thể theo id và postId (đảm bảo quyền sở hữu)
    @Modifying
    @Query("DELETE FROM PostMedia pm WHERE pm.id = :mediaId AND pm.post.id = :postId")
    void deleteByIdAndPostId(@Param("mediaId") Long mediaId, @Param("postId") Long postId);

    // Lấy danh sách ảnh của 1 user (album ảnh trên profile)
    @Query("SELECT pm FROM PostMedia pm WHERE pm.post.user.id = :userId " +
           "AND pm.mediaType = com.example.backend.model.enums.MediaType.image " +
           "AND pm.post.deleteAt IS NULL ORDER BY pm.createAt DESC")
    List<PostMedia> findAllPhotosByUserId(@Param("userId") Long userId);
 
    // Lấy danh sách video của 1 user
    @Query("SELECT pm FROM PostMedia pm WHERE pm.post.user.id = :userId " +
           "AND pm.mediaType = com.example.backend.model.enums.MediaType.video " +
           "AND pm.post.deleteAt IS NULL ORDER BY pm.createAt DESC")
    List<PostMedia> findAllVideosByUserId(@Param("userId") Long userId);
}
