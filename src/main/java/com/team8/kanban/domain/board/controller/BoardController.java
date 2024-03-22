package com.team8.kanban.domain.board.controller;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.board.dto.BoardSectionResponseDto;
import com.team8.kanban.domain.board.service.BoardService;
import com.team8.kanban.global.common.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(userDetails.getUser(),
            boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(CommonResponse.<BoardResponseDto>builder().data(boardResponseDto).msg("보드 생성 완료")
                .build());
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getAllBoards(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardResponseDtos = boardService.getAllBoards(
            userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<List<BoardResponseDto>>builder()
                .data(boardResponseDtos)
                .msg("보드 조회 완료").build());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardSectionResponseDto>> getBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId) {
        BoardSectionResponseDto boardSectionResponseDto = boardService.getBoard(
            userDetails.getUser(), boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<BoardSectionResponseDto>builder().data(boardSectionResponseDto)
                .msg("보드 섹션 조회 완료").build());
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardResponseDto>> updateBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardRequestDto boardRequestDto,
        @PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(userDetails.getUser(),
            boardRequestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<BoardResponseDto>builder().data(boardResponseDto).msg("보드 수정 완료")
                .build());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId) {
        boardService.deleteBoard(userDetails.getUser(), boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<Void>builder().msg("보드 삭제 완료").build());
    }
}
