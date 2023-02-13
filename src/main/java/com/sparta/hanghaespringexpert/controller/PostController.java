package com.sparta.hanghaespringexpert.controller;

import com.sparta.hanghaespringexpert.dto.PostDeleteResponseDto;
import com.sparta.hanghaespringexpert.dto.PostRequestDto;
import com.sparta.hanghaespringexpert.dto.PostResponseDto;
import com.sparta.hanghaespringexpert.dto.PostUpdateResponseDto;
import com.sparta.hanghaespringexpert.entity.Post;
import com.sparta.hanghaespringexpert.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    @GetMapping("/post")
    public List<Post> getPost(HttpServletRequest request){
        return postService.getPost(request);
    }

    @PutMapping("/post/{id}")
    public PostUpdateResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public PostDeleteResponseDto deletePost(@PathVariable Long id, HttpServletRequest request){
        return postService.deletePost(id, request);
    }
}
