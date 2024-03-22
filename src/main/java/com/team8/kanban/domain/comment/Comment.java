package com.team8.kanban.domain.comment;


import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "card_id")
    private Card card;

    public Comment(String content, User user, Card card) {
        this.content = content;
        this.user = user;
        this.card = card;
    }

    public void updateContent(String updateContent) {
        this.content = updateContent;
    }
}
