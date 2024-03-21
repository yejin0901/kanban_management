package com.team8.kanban.domain.card.service;

import com.team8.kanban.domain.card.dto.CardCommentResponse;
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
    List<CardCommentResponse> getCards(Long sectionId);

    /**
     * 단일 카드 조회
     *
     * @param cardId 조회할 카드 번호
     * @return 단일 Card Entity
     */
    CardCommentResponse getCard(Long cardId);

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
     * @param cardIdSet position으로 정렬했을때의 cardId값의 순서
     * @return 변경된 position으로 정렬
     */
    List<CardCommentResponse> changePosition(Long sectionId, Long[] cardIdSet);

    /**
     * 카드 section 변경
     *
     * @param cardId          section을 변경할 cardId
     * @param newSectionId    이동한 Section Id
     * @param newSectionIdSet 바뀐 Section의 현재 position으로 정렬했을때의 cardId값의 순서
     * @param cardPosition    이동한 Section의 position
     * @return List<CardResponse> 변경된 section을 정렬 후 조회
     */
    List<CardCommentResponse> changeSection(Long cardId, Long newSectionId, Long[] newSectionIdSet, Long cardPosition);

    /**
     * 카드에 공통작업자 추가
     *
     * @param user   로그인한 유저의 정보
     * @param userId 작업자로 추가 할 유저 id
     * @param cardId 작업자 추가 할 카드 id
     * @return 성공시 true 반환
     */
    Boolean addUserByCard(User user, Long userId, Long cardId);

    /**
     * 카드에 공통작업자 삭제
     *
     * @param user   로그인한 유저의 정보
     * @param userId 공통작업자에서 삭제 할 유저 id
     * @param cardId 작업자 삭제 할 카드 id
     * @return 성공시 true 반환
     */
    Boolean deleteUserByCard(User user, Long userId, Long cardId);
}