package com.trippin.controller.dto;

import com.trippin.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String content;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }

}

