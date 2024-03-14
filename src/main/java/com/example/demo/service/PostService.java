package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // 필요한 경우 LocalDateTime 사용을 위한 import
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> findAllPosts() {
        return postRepository.findAll().stream().map(post -> convertToPostDTO(post)).collect(Collectors.toList());
    }

    public Optional<PostDTO> findById(Long id) {
        return postRepository.findById(id).map(post -> convertToPostDTO(post));
    }

    public PostDTO save(PostDTO postDTO) {
        // DTO를 엔티티로 변환
        Post post = convertToPostEntity(postDTO);
        // 엔티티 저장
        post = postRepository.save(post);
        // 저장된 엔티티를 다시 DTO로 변환하여 반환
        return convertToPostDTO(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    private PostDTO convertToPostDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setAuthorId(post.getAuthorId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setNumberOfParticipants(post.getNumberOfParticipants());
        dto.setRecruitmentStatus(post.getRecruitmentStatus());
        dto.setTargetAudience(post.getTargetAudience());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setIsDeleted(post.getIsDeleted());
        return dto;
    }

    private Post convertToPostEntity(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId()); // ID는 생성 시에는 설정하지 않거나, 수정 시에만 설정
        post.setAuthorId(dto.getAuthorId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setNumberOfParticipants(dto.getNumberOfParticipants());
        post.setRecruitmentStatus(dto.getRecruitmentStatus());
        post.setTargetAudience(dto.getTargetAudience());
        post.setCreatedAt(dto.getCreatedAt() == null ? LocalDateTime.now() : dto.getCreatedAt()); // 생성 시 현재 시각을 사용
        post.setIsDeleted(dto.getIsDeleted());
        return post;
    }
}
