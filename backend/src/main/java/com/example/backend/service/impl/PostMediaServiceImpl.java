package com.example.backend.service.impl;

import com.example.backend.model.entity.PostMedia;
import com.example.backend.model.entity.Post;
import com.example.backend.model.request.PostMediaRequest;
import com.example.backend.model.respon.PostMediaResponse;
import com.example.backend.model.mapper.PostMediaMapper;
import com.example.backend.repository.PostMediaRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.service.PostMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {

    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;
    private final PostMediaMapper postMediaMapper;

    // Thêm tệp đa phương tiện mới cho bài viết
    @Override
    @Transactional
    public PostMedia createMedia(PostMediaRequest postMediaRequest) {
        if (postMediaRequest == null) {
            throw new IllegalArgumentException("PostMediaRequest cannot be null");
        }
        
        Post post = postRepository.findById(postMediaRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postMediaRequest.getPostId()));
        
        PostMedia postMedia = postMediaMapper.toEntity(postMediaRequest);
        postMedia.setPost(post);
        return postMediaRepository.save(postMedia);
    }

    // Lấy danh sách tệp đa phương tiện của 1 bài viết
    @Override
    public Page<PostMediaResponse> getAllByIdPost(Long post_id) {
        List<PostMedia> mediaList = postMediaRepository.findByPostId(post_id);
        List<PostMediaResponse> responseList = mediaList.stream()
                .map(postMediaMapper::toResponse)
                .toList();
        return new PageImpl<>(responseList);
    }

    // Xóa tệp đa phương tiện theo ID
    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (postMediaRepository.existsById(id)) {
            postMediaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Kiểm tra tệp tồn tại để tải về
    @Override
    public Boolean DowloadMedia(Long id) {
        return postMediaRepository.existsById(id);
    }
}
