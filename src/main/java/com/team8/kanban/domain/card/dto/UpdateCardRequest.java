package com.team8.kanban.domain.card.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateCardRequest {
    private String cardName;
    private String description;
    private LocalDateTime expiredDate;
    private String color;
}
