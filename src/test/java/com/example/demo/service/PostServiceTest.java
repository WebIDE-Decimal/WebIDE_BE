package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        // 이곳에서 필요한 초기화를 수행합니다.
    }

    @Test
    void testFindById() {
        // Given
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Title");
        post.setContent("Test Content");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        Optional<Post> foundPost = postService.findById(postId);

        // Then
        assertTrue(foundPost.isPresent());
        assertEquals(postId, foundPost.get().getId());
        verify(postRepository).findById(postId);
    }

    @Test
    void deleteByIdTest() {
        // Given
        Long postId = 1L;
        doNothing().when(postRepository).deleteById(postId);

        // When
        postService.deleteById(postId);

        // Then
        verify(postRepository, times(1)).deleteById(postId);
    }
}
