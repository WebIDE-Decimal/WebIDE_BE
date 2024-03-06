package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    void getAllPostsTest() throws Exception {
        // Given
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        when(postService.findAll()).thenReturn(Collections.singletonList(post));

        // When & Then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andExpect(view().name("posts"));
    }

    @Test
    void createPostTest() throws Exception {
        Post newPost = new Post();
        newPost.setTitle("New Post");
        newPost.setContent("Content of the new post");

        when(postService.save(any(Post.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Post\",\"content\":\"Content of the new post\"}"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void deletePostTest() throws Exception {
        Long postId = 1L;

        doNothing().when(postService).deleteById(postId);

        mockMvc.perform(get("/posts/delete/{id}", postId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        verify(postService, times(1)).deleteById(postId);
    }

}
