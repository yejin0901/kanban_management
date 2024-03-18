package com.team8.kanban.domain.board.dto;

import com.team8.kanban.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String boardName;
    private String boardColor;

    public BoardResponseDto(Board board) {
        boardId = board.getBoardId();
        boardName = board.getBoardName();
        boardColor = board.getBoardColor();
    }
}
