package com.sparta.hanghaespringexpert.service;

import com.sparta.hanghaespringexpert.dto.LoginRequestDto;
import com.sparta.hanghaespringexpert.dto.LoginResponseDto;
import com.sparta.hanghaespringexpert.dto.SignupRequestDto;
import com.sparta.hanghaespringexpert.dto.SignupResponseDto;
import com.sparta.hanghaespringexpert.entity.User;
import com.sparta.hanghaespringexpert.jwt.JwtUtil;
import com.sparta.hanghaespringexpert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다."); //등록 실패 시 예외 발생됨
        }

        User user = userRepository.save(new User(username, password));

        return new SignupResponseDto("가입 성공","200");
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        User foundUser = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );

        if(!(foundUser.getPassword().equals(loginRequestDto.getPassword()))){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(foundUser.getUsername()));

        return new LoginResponseDto("로그인 성공","200");
    }
}
