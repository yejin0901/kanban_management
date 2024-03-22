package com.team8.kanban.domain.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentByCardResponse {

    private Long commentId;
    private Long cardId;
    private String commentUsername;
    private String commentContent;

}