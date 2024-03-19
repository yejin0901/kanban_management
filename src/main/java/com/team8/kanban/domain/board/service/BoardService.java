package com.team8.kanban.domain.board.service;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.board.entity.BoardUser;
import com.team8.kanban.domain.board.repository.BoardRepository;
import com.team8.kanban.domain.board.repository.BoardUserRepository;
import com.team8.kanban.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;

    @Transactional
    public BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
        BoardUser boardUser = new BoardUser(board, user);
        boardUserRepository.save(boardUser);
        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getBoard(User user) {
        List<BoardUser> boardUsers = boardUserRepository.findAllByUserId(user.getId());
        List<Board> boards = new ArrayList<>();
        for (BoardUser boardUser : boardUsers) {
            boards.add(boardUser.getBoard());
        }
        return boards.stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(User user, BoardRequestDto boardRequestDto, Long boardId) {
        Board board = findBoard(boardId);
        validateUser(user, board);
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(User user, Long boardId) {
        existBoard(boardId);
        validateCreatedUser(user, boardId);
        boardRepository.deleteById(boardId);
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 보드는 존재하지 않습니다."));
    }

    private void existBoard(Long boardId) {
        boolean flag = boardRepository.existsById(boardId);
        if (!flag) {
            throw new IllegalArgumentException("해당 보드는 존재하지 않습니다.");
        }
    }

    private void validateUser(User user, Board board) {
        if (!boardUserRepository.existsByBoardAndUser(board, user)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    private void validateCreatedUser(User user, Long boardId) {
        if (!boardId.equals(user.getId())) {
            throw new IllegalArgumentException("보드를 생성한 유저가 아닙니다.");
        }
    }
}