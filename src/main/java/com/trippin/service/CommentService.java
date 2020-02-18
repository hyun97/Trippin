package com.trippin.service;

import com.trippin.controller.dto.CommentDto;
import com.trippin.domain.Comment;
import com.trippin.domain.CommentRepository;
import com.trippin.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void createComment(Long postId, CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(postRepository.findById(postId).orElse(null))
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
