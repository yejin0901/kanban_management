package com.team8.kanban.domain.board.controller;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.board.service.BoardService;
import com.team8.kanban.global.exception.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(userDetails.getUser(), boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(CommonResponse.<BoardResponseDto>builder().data(boardResponseDto).build());
    }

    @GetMapping("/boards")
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardResponseDtos = boardService.getBoard(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<BoardResponseDto>>builder().data(boardResponseDtos).build());
    }

    @PutMapping("/boards/{boardId}")
    public ResponseEntity<CommonResponse<BoardResponseDto>> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardRequestDto boardRequestDto,
            @PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(userDetails.getUser(), boardRequestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<BoardResponseDto>builder().data(boardResponseDto).build());
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {
        boardService.deleteBoard(userDetails.getUser(), boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Void>builder().msg("보드가 삭제되었습니다.").build());
    }

}
