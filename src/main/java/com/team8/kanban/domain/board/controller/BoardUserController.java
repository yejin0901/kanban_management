package com.team8.kanban.domain.board.controller;

import com.team8.kanban.domain.board.dto.BoardUserRequestDto;
import com.team8.kanban.domain.board.dto.BoardUserResponseDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/invitation")
public class BoardUserController {

    private final BoardService boardService;

    @PostMapping("/{boardId}")
    public ResponseEntity<CommonResponse<List<BoardUserResponseDto>>> inviteBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @RequestBody BoardUserRequestDto boardUserRequestDto) {
        List<BoardUserResponseDto> boardUserResponseDtos = boardService.inviteBoardUser(
            userDetails.getUser(), boardId, boardUserRequestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<List<BoardUserResponseDto>>builder().data(boardUserResponseDtos)
                .msg("보드 초대 완료").build());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResponse<List<BoardUserResponseDto>>> getBoardUsers(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId) {
        List<BoardUserResponseDto> boardUserResponseDtos = boardService.getBoardUsers(
            userDetails.getUser(), boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<List<BoardUserResponseDto>>builder().data(boardUserResponseDtos)
                .msg("보드 유저 조회 완료").build());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<List<BoardUserResponseDto>>> deleteBoardUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @RequestBody BoardUserRequestDto boardUserRequestDto) {
        List<BoardUserResponseDto> boardUserResponseDtos = boardService.deleteBoardUser(
            userDetails.getUser(), boardId, boardUserRequestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(CommonResponse.<List<BoardUserResponseDto>>builder().data(boardUserResponseDtos)
                .msg("보드 유저 삭제 완료").build());
    }
}
