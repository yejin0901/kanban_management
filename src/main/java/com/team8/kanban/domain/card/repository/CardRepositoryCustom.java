package com.team8.kanban.domain.card.repository;

import com.team8.kanban.domain.card.dto.CardCommentResponse;
import com.team8.kanban.domain.card.entity.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CardRepositoryCustom {

    /**
     * 단일 카드 조회
     *
     * @param cardId 조회 할 카드번호
     * @return CardCommentResponse
     */
    CardCommentResponse findCard(Long cardId);

    /**
     * sectionId에 해당하는 section의 전체 카드 조회
     *
     * @param sectionId 조회할 section의 Id값
     * @return List<CardCommentResponse>
     */
    List<CardCommentResponse> findCardsV1(Long sectionId);

    /**
     * sectionId에 해당하는 section의 전체 카드 조회 (Slice처리O)
     *
     * @param sectionId 조회할 section의 Id값
     * @param pageable  페이징처리 값
     * @return Slice<CardCommentResponse>
     */
    Slice<CardCommentResponse> findCards(Long sectionId, Pageable pageable);

    /**
     * sectionId에 해당하는 section의 전체 카드 조회
     *
     * @param sectionId position 변경해야할 section의 Id
     * @return List<Card>
     */
    List<Card> findCardsBySectionId(Long sectionId);
}