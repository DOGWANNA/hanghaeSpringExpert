package com.sparta.hanghaespringexpert.dto;

import com.sparta.hanghaespringexpert.entity.User;
import lombok.Getter;


@Getter
public class PostRequestDto {
    private String title;
    private String content;
}
