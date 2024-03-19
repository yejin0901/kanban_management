package com.team8.kanban.domain.card.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionChangeRequest {

    @Positive
    @NotNull
    private Long cardId;

    @Positive
    @NotNull
    private Long newSectionId;

    @NotEmpty
    private String newSectionIdSet;

    @Positive
    @NotNull
    private Long cardPositionId;

}
