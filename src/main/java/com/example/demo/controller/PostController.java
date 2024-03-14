package com.example.demo.controller;

import com.example.demo.dto.PostDTO;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional; // error 수정

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 게시글을 조회하여 모델에 추가하는 메소드
    @GetMapping
    public String listPosts(Model model) {
        List<PostDTO> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts"; // posts.html 템플릿 반환
    }

    // 특정 ID를 가진 게시글을 조회하는 메소드
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<PostDTO> postDto = postService.findById(id);
        postDto.ifPresentOrElse(
                dto -> model.addAttribute("post", dto),
                () -> new IllegalArgumentException("Invalid post Id:" + id)
        );
        return "detail"; // detail.html 템플릿 반환
    }

    // 게시글 생성 폼을 보여주는 메소드
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "create"; // create.html 템플릿 반환
    }

    // 사용자 입력을 통해 새 게시글을 생성하는 메소드
    @PostMapping
    public String createPost(@ModelAttribute PostDTO postDTO, RedirectAttributes redirectAttributes) {
        postService.save(postDTO);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 생성되었습니다.");
        return "redirect:/posts";
    }

    // 게시글 수정 폼을 보여주는 메소드
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<PostDTO> postDto = postService.findById(id);
        postDto.ifPresentOrElse(
                dto -> model.addAttribute("post", dto),
                () -> new IllegalArgumentException("Invalid post Id:" + id)
        );
        return "edit"; // edit.html 템플릿 반환
    }

    // 사용자 입력을 통해 게시글을 업데이트하는 메소드
    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute PostDTO postDTO, RedirectAttributes redirectAttributes) {
        postDTO.setId(id); // URL에서 받은 ID를 DTO에 설정
        postService.save(postDTO);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 업데이트되었습니다.");
        return "redirect:/posts";
    }

    // 특정 ID를 가진 게시글을 삭제하는 메소드
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
        return "redirect:/posts";
    }
}
