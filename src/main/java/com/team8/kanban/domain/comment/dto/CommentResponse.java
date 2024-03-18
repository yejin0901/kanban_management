package com.team8.kanban.domain.comment.dto;

import com.team8.kanban.domain.comment.Comment;
import com.team8.kanban.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public CommentResponse(Comment comment , User user) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = user.getUsername();
        this.createAt = comment.getCreatedAt();
        this.updateAt = comment.getModifiedAt();
    }
}
