package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    private String cardName;
    private String description;
    private LocalDate expiredDate;
    private Color color;

    public void update(UpdateCardRequest request) {
        this.cardName = request.getCardName();
        this.description = request.getDescription();
        this.expiredDate = request.getExpiredDate();
        this.color = Color.decode(request.getColor());
    }
}
