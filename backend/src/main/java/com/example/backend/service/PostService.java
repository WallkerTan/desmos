package com.example.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.backend.model.entity.Post;
import com.example.backend.model.request.PostRequest;

public interface PostService {
    // post
    //lay theo id
    Post getPostById(Long id);
    //lay theo ten + phaan trang
    Page<Post> findByCaption(String caption,Pageable pageable);
    // đăng bài
    Post createPost(PostRequest postRequest);
    // chỉnh sửa bài
    Boolean updatePost(Long id,PostRequest postRequest);
    //xoa
    Boolean deletePost(long id);
}
