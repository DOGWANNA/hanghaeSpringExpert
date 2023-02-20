package com.sparta.hanghaespringexpert.service;

import com.sparta.hanghaespringexpert.dto.PostDeleteResponseDto;
import com.sparta.hanghaespringexpert.dto.PostRequestDto;
import com.sparta.hanghaespringexpert.dto.PostResponseDto;
import com.sparta.hanghaespringexpert.dto.PostUpdateResponseDto;
import com.sparta.hanghaespringexpert.entity.Post;
import com.sparta.hanghaespringexpert.entity.User;
import com.sparta.hanghaespringexpert.entity.UserRoleEnum;
import com.sparta.hanghaespringexpert.jwt.JwtUtil;
import com.sparta.hanghaespringexpert.repository.PostRepository;
import com.sparta.hanghaespringexpert.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.combo(request);

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user));

            return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPost(HttpServletRequest request) {
        Claims claims = jwtUtil.combo(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 없습니다.")
        );

        List<Post> postList = postRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for(Post post : postList){
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        Claims claims = jwtUtil.combo(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 없습니다.")
        );

        Post post = postRepository.findByIdAndUserId(id , user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );

        UserRoleEnum userRoleEnum = user.getRole();
        // 사용자 권한 가져와서 ADMIN 이면 전체 수정 가능, USER 면 본인의 게시물만 수정 가능
        if (userRoleEnum == UserRoleEnum.USER){
            // 댓글의 id와 현재 로그인한 user의 id를 비교하여 본인의 게시물만 수정 가능
            if(post.getUser().getId().equals(user.getId())){
                post.update(postRequestDto);
            }else {
                throw new IllegalArgumentException("해당 유저의 댓글이 아닙니다.");
            }
        }else { // 관리자면 모두 다 가능
            post.update(postRequestDto);
        }

        return new PostUpdateResponseDto(postRequestDto);
    }

    @Transactional
    public PostResponseDto deletePost(Long id, HttpServletRequest request) {
        Claims claims = jwtUtil.combo(request);

        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 없습니다.")
        );

        Post post = postRepository.findByIdAndUserId(id , user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다.")
        );

        UserRoleEnum userRoleEnum = user.getRole();
        // 사용자 권한 가져와서 ADMIN 이면 전체 수정 가능, USER 면 본인의 게시물만 수정 가능
        if (userRoleEnum == UserRoleEnum.USER){
            // 댓글의 id와 현재 로그인한 user의 id를 비교하여 본인의 게시물만 수정 가능
            if(post.getUser().getId().equals(user.getId())){
                postRepository.deleteById(user.getId());
            }else {
                throw new IllegalArgumentException("해당 유저의 게시글이 아닙니다.");
            }
        }else { // 관리자면 모두 다 가능
            postRepository.deleteById(user.getId());
        }

        return new PostResponseDto(post);
    }

}
