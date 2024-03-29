package com.team8.kanban.domain.card.entity;

import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.global.entity.ColorEnum;
import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Table(name = "cards", indexes = {
        @Index(name = "idx_card_name", columnList = "cardName"),
        @Index(name = "idx_card_id", columnList = "cardId"),
        @Index(name = "idx_card_sectionId", columnList = "sectionId"),
        @Index(name = "idx_position", columnList = "position")
})
@NoArgsConstructor
@AllArgsConstructor
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String cardName;

    private String description;

    private Long userid;

    private String username;

    private LocalDateTime expiredDate;

    private ColorEnum colorEnum;

    private Long sectionId;

    private Long position;

    public void update(UpdateCardRequest request) {
        if (request.getCardName() != null) this.cardName = request.getCardName();
        if (request.getDescription() != null) this.description = request.getDescription();
        if (request.getExpiredDate() != null) this.expiredDate = request.getExpiredDate();
        if (request.getColor() != null) this.colorEnum = ColorEnum.valueOf(request.getColor());
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void setSection(long newSectionId) {
        this.sectionId = newSectionId;
    }
}
