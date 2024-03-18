package com.team8.kanban.domain.card;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateCardRequest {
    private String cardName;
    private String description;
    private LocalDate expiredDate;
    private String color;

}
