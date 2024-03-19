package com.team8.kanban.domain.board.entity;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String boardName;

    @Column
    private String boardColor;

    @Column
    private Long createdUserId;

    public Board(BoardRequestDto boardRequestDto, User user) {
        this.boardName = boardRequestDto.getBoardName();
        this.boardColor = boardRequestDto.getColor();
        this.createdUserId = user.getId();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.boardName = boardRequestDto.getBoardName();
        this.boardColor = boardRequestDto.getColor();
    }
}
