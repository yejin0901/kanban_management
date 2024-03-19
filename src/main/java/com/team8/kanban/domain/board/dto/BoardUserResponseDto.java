package com.team8.kanban.domain.board.dto;

import com.team8.kanban.domain.board.entity.BoardUser;
import lombok.Getter;

@Getter
public class BoardUserResponseDto {

    private Long boardUserId;

    public BoardUserResponseDto(BoardUser boardUser) {
        this.boardUserId = boardUser.getUser().getId();
    }
}
