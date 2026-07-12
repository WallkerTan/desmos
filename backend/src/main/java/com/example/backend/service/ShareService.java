package com.example.backend.service;

import com.example.backend.model.request.ShareRequest;
import com.example.backend.model.respon.ShareResponse;

public interface ShareService {
    // chia sẻ bài viết
    ShareResponse sharePost(Long userId, ShareRequest request);

    // xóa bài chia sẻ
    Boolean deleteShare(Long userId, Long shareId);
}
