package com.team8.kanban.domain.board.dto;

import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.section.SectionResponseDto;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardSectionResponseDto {

    private Long boardId;
    private String boardName;
    private String boardColor;
    private Long createdUserId;
    private List<BoardUserResponseDto> boardUserResponseDtos;
    private List<SectionResponseDto> sectionResponseDtos;

    public BoardSectionResponseDto(Board board, List<BoardUserResponseDto> boardUserResponseDtos,
        List<SectionResponseDto> sectionResponseDtos) {
        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
        this.boardColor = board.getBoardColor().toString();
        this.createdUserId = board.getCreatedUserId();
        this.boardUserResponseDtos = boardUserResponseDtos;
        this.sectionResponseDtos = sectionResponseDtos;
    }
}
