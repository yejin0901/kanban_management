package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
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
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String cardName;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    private LocalDateTime expiredDate;

    private ColorEnum colorEnum;

    public void update(UpdateCardRequest request) {
        this.cardName = request.getCardName();
        this.description = request.getDescription();
        this.expiredDate = request.getExpiredDate();
        this.colorEnum = ColorEnum.valueOf(request.getColor());
    }
}
