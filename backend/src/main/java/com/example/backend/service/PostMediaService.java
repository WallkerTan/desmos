package com.example.backend.service;

import org.springframework.data.domain.Page;
import com.example.backend.model.entity.PostMedia;
import com.example.backend.model.request.PostMediaRequest;
import com.example.backend.model.respon.PostMediaResponse;

public interface PostMediaService {
    // thêm tệp mới
    PostMedia createMedia(PostMediaRequest postMediaRequest);

    // lấy ds tệp của 1 bài post
    Page<PostMediaResponse> getAllByIdPost(Long post_id);

    // xóa tệp
    Boolean deleteById(Long id);

    // tải về
    Boolean DowloadMedia(Long id);
}
