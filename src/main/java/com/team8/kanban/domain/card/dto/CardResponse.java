package com.team8.kanban.domain.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private Long cardId;
    private String cardName;
    private String description;
    private String username;
    private LocalDateTime expiredDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
