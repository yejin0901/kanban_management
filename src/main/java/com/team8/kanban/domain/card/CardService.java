package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;

import java.util.List;

public interface CardService {

    /**
     * sectionId에 해당하는 section의 전체 카드 조회
     *
     * @param sectionId 조회할 section의 Id값
     * @return 해당 section의 Card Entity
     */
    List<CardResponse> getCards(Long sectionId);

    /**
     * 단일 카드 조회
     *
     * @param cardId 조회할 카드 번호
     * @return 단일 Card Entity
     */
    CardResponse getCard(Long cardId);


    /**
     * 카드 신규 생성
     *
     * @param user    로그인한 유저의 정보
     * @param request 생성할 카드 내용
     * @return 생성된 Card Entity
     */
    CardResponse createCard(User user, CreateCardRequest request);

    /**
     * 카드 내용 수정
     *
     * @param user    로그인한 유저의 정보
     * @param request 수정할 카드 내용
     * @param cardId  수정할 카드 번호
     * @return 수정된 Card Entity
     */
    CardResponse updateCard(User user, UpdateCardRequest request, Long cardId);

    /**
     * 카드 삭제
     *
     * @param user   로그인한 유저의 정보
     * @param cardId 삭제할 카드 번호
     * @return 성공시 true
     */
    Boolean deleteCard(User user, Long cardId);

    /**
     * 카드 position 변경
     *
     * @param sectionId position을 변경할 sectionId
     * @param cardIdSet id가 입력된 순서대로 position을 순차입력
     * @return 해당
     */
    List<CardResponse> changePosition(Long sectionId, String cardIdSet);

}

