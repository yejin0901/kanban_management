package com.team8.kanban.domain.board.service;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.board.dto.BoardUserRequestDto;
import com.team8.kanban.domain.board.dto.BoardUserResponseDto;
import com.team8.kanban.domain.user.User;
import java.util.List;

public interface BoardService {

    /**
     * 보드 생성
     *
     * @param user 로그인한 유저 정보
     * @param boardRequestDto 생성할 보드 정보
     * @return 생성한 보드
     */
    BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto);

    /**
     * 전체 보드 조회
     *
     * @param user 로그인한 유저 정보
     * @return 소유한 보드 리스트
     */
    List<BoardResponseDto> getBoard(User user);

    /**
     * 보드 수정
     *
     * @param user 로그인한 유저 정보
     * @param boardRequestDto 수정할 보드 정보
     * @param boardId 수정할 보드 ID
     * @return 수정된 보드
     */
    BoardResponseDto updateBoard(User user, BoardRequestDto boardRequestDto, Long boardId);

    /**
     * 보드 삭제
     *
     * @param user 로그인한 유저 정보
     * @param boardId 삭제할 보드 ID
     */
    void deleteBoard(User user, Long boardId);

    /**
     *보드 초대
     *
     * @param user 로그인한 유저 정보
     * @param boardId 초대할 보드 ID
     * @param boardUserRequestDto 초대할 유저 ID 리스트
     * @return 보드 사용 유저 리스트
     */
    List<BoardUserResponseDto> inviteBoardUser(User user, Long boardId,
        BoardUserRequestDto boardUserRequestDto);

    /**
     *보드 유저 조회
     *
     * @param user 로그인한 유저 정보
     * @param boardId 조회할 보드 ID
     * @return 보드 사용 유저 리스트
     */
    List<BoardUserResponseDto> getBoardUsers(User user, Long boardId);

    /**
     * 보드 유저 삭제
     *
     * @param user                로그인한 유저 정보
     * @param boardId             유저를 삭제할 보드 ID
     * @param boardUserRequestDto 삭제할 유저 ID 리스트
     * @return 보드 사용 유저 리스트
     */
    List<BoardUserResponseDto> deleteBoardUser(User user, Long boardId,
        BoardUserRequestDto boardUserRequestDto);
}
