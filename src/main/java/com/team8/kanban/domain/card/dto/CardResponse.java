package com.team8.kanban.domain.card.dto;

import com.team8.kanban.domain.card.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private Long cardId;
    private String cardName;
    private String description;
    private String username;
    private LocalDateTime expiredDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long sectionId;
    private Long position;

    // query dsl 사용 시, 이용했습니다.
    public CardResponse(Card card) {
        this.cardId = card.getCardId();
        this.cardName = getCardName();
    }
}
