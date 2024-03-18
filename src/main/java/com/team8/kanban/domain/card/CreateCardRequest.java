package com.team8.kanban.domain.card;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateCardRequest {
    @NotEmpty
    private String cardName;
    @NotEmpty
    private String description;
    @NotEmpty
    private LocalDate expiredDate;
    @NotEmpty
    private String color;
}
