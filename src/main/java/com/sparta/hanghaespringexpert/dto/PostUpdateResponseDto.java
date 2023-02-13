package com.sparta.hanghaespringexpert.dto;

import lombok.Getter;

@Getter
public class PostUpdateResponseDto {
    private String title;
    private String content;

    public PostUpdateResponseDto(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }
}
