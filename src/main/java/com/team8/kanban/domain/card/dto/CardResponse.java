package com.team8.kanban.domain.card.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CardResponse {

    private Long cardId;
    private String cardName;
    private String description;
    private LocalDateTime expiredDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
