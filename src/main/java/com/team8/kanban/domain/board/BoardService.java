package com.team8.kanban.domain.board;

import com.team8.kanban.domain.board.dto.BoardRequestDto;
import com.team8.kanban.domain.board.dto.BoardResponseDto;
import com.team8.kanban.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }
}
