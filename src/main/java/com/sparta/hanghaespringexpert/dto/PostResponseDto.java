package com.sparta.hanghaespringexpert.dto;

import com.sparta.hanghaespringexpert.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String title;
    private String content;

    private String username;


    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
    }

}
