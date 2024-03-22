package com.team8.kanban.domain.section;

import com.team8.kanban.domain.card.entity.Card;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CardProjection {
    private Long cardId;
    private String cardName;
    private String description;
    private Long sectionId;
}
