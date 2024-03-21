package com.team8.kanban.domain.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCommentResponse {

    private Long cardId;
    private String cardName;
    private String description;
    private String username;
    private LocalDateTime expiredDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long sectionId;
    private Long position;
    private List<CommentByCardResponse> comments;

    public CardCommentResponse(CardResponse cardResponse, List<CommentByCardResponse> commentByCardResponses) {
        this.cardId = cardResponse.getCardId();
        this.cardName = cardResponse.getCardName();
        this.description = cardResponse.getDescription();
        this.username = cardResponse.getUsername();
        this.expiredDate = cardResponse.getExpiredDate();
        this.createdAt = cardResponse.getCreatedAt();
        this.sectionId = cardResponse.getSectionId();
        this.position = cardResponse.getPosition();
        this.comments = commentByCardResponses;
    }
}
