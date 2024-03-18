package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.exception.CommonResponse;

public interface CardService {

    /**
     * 카드 신규 생성
     *
     * @param user    로그인한 유저의 정보
     * @param request 생성할 카드 내용
     * @return CommonResponse (msg:생성완료 / 생성된 Card Entity)
     */
    CommonResponse<CardResponse> createCard(User user, CreateCardRequest request);

    /**
     * 카드 내용 수정
     *
     * @param user    로그인한 유저의 정보
     * @param request 수정할 카드 내용
     * @param cardId  수정할 카드 번호
     * @return CommonResponse (msg:수정완료 / 수정된 Card Entity)
     */
    CommonResponse<CardResponse> updateCard(User user, UpdateCardRequest request, Long cardId);

    /**
     * 카드 삭제
     *
     * @param user   로그인한 유저의 정보
     * @param cardId 삭제할 카드 번호
     * @return 성공시 true 리턴
     */
    CommonResponse<Boolean> deleteCard(User user, Long cardId);
}

