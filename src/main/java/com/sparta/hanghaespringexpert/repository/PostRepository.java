package com.sparta.hanghaespringexpert.repository;

import com.sparta.hanghaespringexpert.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository <Post, Long> {
    List<Post> findAllByUserId(Long id);
    Optional<Post> findByIdAndUserId(Long id, Long id1);
}
