package com.team8.kanban.domain.board.service;

import static com.team8.kanban.global.exception.ErrorCode.BOARD_AUTHORIZED;
import static com.team8.kanban.global.exception.ErrorCode.BOARD_NOT_FOUND;
import static com.team8.kanban.global.exception.ErrorCode.BOARD_USER_NOT_FOUND;
import static com.team8.kanban.global.exception.ErrorCode.NOT_BOARD_CREATED_USER;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.board.dto.BoardSectionResponseDto;
import com.team8.kanban.domain.board.dto.BoardUserRequestDto;
import com.team8.kanban.domain.board.dto.BoardUserResponseDto;
import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.board.entity.BoardUser;
import com.team8.kanban.domain.board.repository.BoardQueryRepository;
import com.team8.kanban.domain.board.repository.BoardRepository;
import com.team8.kanban.domain.board.repository.BoardUserRepository;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.domain.user.UserRepository;
import com.team8.kanban.global.exception.customException.NotAuthorizedException;
import com.team8.kanban.global.exception.customException.NotFoundException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
        BoardUser boardUser = new BoardUser(board, user);
        boardUserRepository.save(boardUser);

        return new BoardResponseDto(board);
    }

    @Override
    public List<BoardResponseDto> getAllBoards(User user) {

        return boardQueryRepository.findAllBoard(user);
    }

    @Override
    public BoardSectionResponseDto getBoard(User user, Long boardId) {
        Board board = findBoard(boardId);

        return boardQueryRepository.findBoard(board, user);
    }

    @Override
    @Transactional
    public BoardResponseDto updateBoard(User user, BoardRequestDto boardRequestDto, Long boardId) {
        Board board = findBoard(boardId);
        validateUser(user, board);
        board.update(boardRequestDto);

        return new BoardResponseDto(board);
    }

    @Override
    @Transactional
    public void deleteBoard(User user, Long boardId) {
        Board board = findBoard(boardId);
        validateCreatedUser(user, board);
        boardUserRepository.deleteAllByBoard(board);
        boardRepository.deleteById(boardId);
    }

    @Override
    @Transactional
    public List<BoardUserResponseDto> inviteBoardUser(User user, Long boardId,
        BoardUserRequestDto boardUserRequestDto) {
        Board board = findBoard(boardId);
        validateUser(user, board);
        List<Long> invitedUserIds = boardUserRequestDto.getUserIds();

        for (Long invitedUserId : invitedUserIds) {
            User invitedUser = userRepository.findById(invitedUserId).orElseThrow(
                () -> new NotFoundException(BOARD_USER_NOT_FOUND));
            if (boardUserRepository.existsByBoardAndUser(board, invitedUser)) {
                throw new IllegalArgumentException("이미 초대된 유저입니다.");
            }
            BoardUser boardUser = new BoardUser(board, invitedUser);
            boardUserRepository.save(boardUser);
        }

        List<BoardUser> boardUsers = boardUserRepository.findAllByBoard(board);
        return boardUsers.stream().map(BoardUserResponseDto::new).toList();
    }

    @Override
    public List<BoardUserResponseDto> getBoardUsers(User user, Long boardId) {
        Board board = findBoard(boardId);
        validateUser(user, board);
        List<BoardUser> boardUsers = boardUserRepository.findAllByBoard(board);

        return boardUsers.stream().map(BoardUserResponseDto::new).toList();
    }

    @Transactional
    @Override
    public List<BoardUserResponseDto> deleteBoardUser(User user, Long boardId,
        BoardUserRequestDto boardUserRequestDto) {

        Board board = findBoard(boardId);
        validateCreatedUser(user, board);
        List<Long> deleteUserIds = boardUserRequestDto.getUserIds();

        for (Long deleteUserId : deleteUserIds) {
            if (deleteUserId.equals(user.getId())) {
                throw new IllegalArgumentException("보드 생성자는 이용 유저에서 삭제될 수 없습니다.");
            }
            BoardUser boardUser = boardUserRepository.findByBoardAndUserId(board, deleteUserId);
            boardUserRepository.delete(boardUser);
        }

        List<BoardUser> boardUsers = boardUserRepository.findAllByBoard(board);
        return boardUsers.stream().map(BoardUserResponseDto::new).toList();
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
            () -> new NotFoundException(BOARD_NOT_FOUND));
    }

    private void validateUser(User user, Board board) {
        if (!boardUserRepository.existsByBoardAndUser(board, user)) {
            throw new NotAuthorizedException(BOARD_AUTHORIZED);
        }
    }

    private void validateCreatedUser(User user, Board board) {
        if (!board.getCreatedUserId().equals(user.getId())) {
            throw new NotAuthorizedException(NOT_BOARD_CREATED_USER);
        }
    }
}
