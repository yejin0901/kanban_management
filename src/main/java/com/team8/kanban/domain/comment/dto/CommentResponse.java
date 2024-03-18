package com.team8.kanban.domain.comment.dto;

import com.team8.kanban.domain.comment.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {

    private Long id;
    private String content;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
