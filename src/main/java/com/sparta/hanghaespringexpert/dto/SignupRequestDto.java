package com.sparta.hanghaespringexpert.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[0-9a-zA-Z]*$")
    private String password;
}
