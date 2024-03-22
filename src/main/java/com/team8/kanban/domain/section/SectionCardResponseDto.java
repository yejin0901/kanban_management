package com.team8.kanban.domain.section;


import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.entity.Card;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SectionCardResponseDto {
    private Long sectionId;
    private String sectionName;
    private List<CardProjection> cardList = new ArrayList<>();

    public SectionCardResponseDto(SectionResponseDto s, List<CardProjection> c){
        sectionId = s.getSectionId();
        sectionName = s.getSectionName();
        cardList = c;
    }
}
