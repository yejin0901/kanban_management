package com.team8.kanban.domain.section;

import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sections")
public class Section extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String columnName;


// Entity 생성 후 추가
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "board_id", nullable = false)
//    private Board board;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "card_id", nullable = false)
//    private Card card;

    public Section(String name) {
        this.columnName = name;
    }

    public void update(String columnName) {
        this.columnName = columnName;
    }
}
