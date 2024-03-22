package com.team8.kanban.domain.section;

import com.team8.kanban.domain.board.entity.Board;

import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "sections", indexes = {
        @Index(name = "idx_user_next", columnList = "next"),
        @Index(name = "idx_user_id", columnList = "id")
})
public class Section extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sectionName;

    @Column
    private Long next;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Section(SectionRequestDto requestDto, Board board) {
        this.sectionName = requestDto.getSectionName();
        this.board = board;
        this.next = null;
    }

    public void updateName(String sectionName) {
        this.sectionName = sectionName;
    }
    public void updateNextPos(Long next) {
        this.next = next;
    }
}
