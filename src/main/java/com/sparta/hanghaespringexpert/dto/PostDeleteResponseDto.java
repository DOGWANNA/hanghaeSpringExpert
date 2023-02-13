package com.sparta.hanghaespringexpert.dto;

import lombok.Getter;

@Getter
public class PostDeleteResponseDto {
    private String message;
    private String httpCode;

    public PostDeleteResponseDto(String message, String httpCode){
        this.message = message;
        this.httpCode = httpCode;
    }
}
