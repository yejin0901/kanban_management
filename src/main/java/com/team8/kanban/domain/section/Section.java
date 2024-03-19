package com.team8.kanban.domain.section;

import com.team8.kanban.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "sections")
public class Section extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sectionName;

    @Column
    private Long next;


// Entity 생성 후 추가
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "board_id", nullable = false)
//    private Board board;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "card_id", nullable = false)
//    private Card card;

    public Section(SectionRequestDto requestDto) {
        this.sectionName = requestDto.getSectionName();
    }

    public void updateName(String sectionName) {
        this.sectionName = sectionName;
    }
    public void updatePos(Long next) {
        this.next = next;
    }
}
