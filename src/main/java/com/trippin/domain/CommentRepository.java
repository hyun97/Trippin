package com.trippin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    List<Comment> findByIdAndPostIdAndAuthorId(Long commentId, Long postId, Long userId);

    Integer countByPostId(Long postId);

}
