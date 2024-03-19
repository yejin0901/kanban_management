package com.team8.kanban.domain.card.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateCardRequest {
    @NotEmpty
    private String cardName;
    @NotEmpty
    private String description;
    @NotEmpty
    private LocalDateTime expiredDate;
    @NotEmpty
    private String color;
    @NotEmpty
    private Long sectionId;
}
