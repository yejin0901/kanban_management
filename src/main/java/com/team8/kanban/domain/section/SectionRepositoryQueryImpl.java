package com.team8.kanban.domain.section;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.team8.kanban.domain.card.entity.QCard.card;
import static com.team8.kanban.domain.section.QSection.section;

@RequiredArgsConstructor
public class SectionRepositoryQueryImpl implements SectionRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SectionCardResponseDto> findSectionAndCard(List<SectionResponseDto> sectionList) {
        // 최적화 시, projection과 index 적용예정
        List<CardProjection> cards = jpaQueryFactory
                .select(Projections.fields(
                        CardProjection.class,
                        card.cardId,
                        card.cardName,
                        card.description,
                        card.sectionId))
                .from(card)
                .fetch();

        // Card를 Section ID 기준으로 그룹화
        Map<Long, List<CardProjection>> cardsBySection = cards.stream()
                .collect(Collectors.groupingBy(CardProjection::getSectionId));

        // SectionResponseDto 리스트를 기반으로 SectionCardResponseDto 리스트 생성
        return sectionList.stream().map(sectionResponseDto -> {
            List<CardProjection> cardResponses = cardsBySection.getOrDefault(sectionResponseDto.getSectionId(), Collections.emptyList());
            return new SectionCardResponseDto(sectionResponseDto, cardResponses);
        }).collect(Collectors.toList());
    }

    @Override
    public SectionProjection findSectionById(Long Id) {
        return jpaQueryFactory
                .select(Projections.fields(
                        SectionProjection.class,
                        section.id.as("sectionId"),
                        section.sectionName,
                        section.next))
                .from(section)
                .where(section.id.eq(Id))
                .fetchOne();
    }
}

