package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;

import java.util.List;

public interface CardRepositoryCustom {

    /**
     * 단일 카드 조회
     *
     * @param cardId 조회 할 카드번호
     * @return CardResponse
     */
    CardResponse findCard(Long cardId);

    /**
     * 전체 카드 조회
     *
     * @return List<CardResponse>
     */
    List<CardResponse> findCards();
}