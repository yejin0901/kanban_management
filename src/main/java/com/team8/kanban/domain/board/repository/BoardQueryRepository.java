package com.team8.kanban.domain.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team8.kanban.domain.board.dto.BoardSectionResponseDto;
import com.team8.kanban.domain.board.dto.BoardUserResponseDto;
import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.board.entity.BoardUser;
import com.team8.kanban.domain.board.entity.QBoard;
import com.team8.kanban.domain.board.entity.QBoardUser;
import com.team8.kanban.domain.section.QSection;
import com.team8.kanban.domain.section.SectionResponseDto;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.exception.ErrorCode;
import com.team8.kanban.global.exception.customException.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private QSection qSection = QSection.section;

    private QBoardUser qBoardUser = QBoardUser.boardUser;

    private QBoard qBoard = QBoard.board;

    public BoardSectionResponseDto findBoard(Long boardId, User user) {

        Board board = jpaQueryFactory
            .selectFrom(qBoard)
            .where(qBoard.boardId.eq(boardId))
            .fetchOne();

        if (board == null) {
            throw new NotFoundException(ErrorCode.BOARD_NOT_FOUND);
        }

        List<BoardUser> boardUsers = jpaQueryFactory
            .selectFrom(qBoardUser)
            .leftJoin(qBoard).on(qBoard.boardId.eq(qBoardUser.board.boardId))
            .fetchJoin()
            .where(qBoardUser.board.boardId.eq(boardId))
            .fetch();

        List<BoardUserResponseDto> boardUserResponseDtos = boardUsers.stream()
            .map(BoardUserResponseDto::new).toList();

        List<SectionResponseDto> sectionResponseDtos = jpaQueryFactory
            .select(Projections.constructor(SectionResponseDto.class, qSection))
            .from(qSection)
            .where(qSection.board.boardId.eq(boardId))
            .fetch();

        return new BoardSectionResponseDto(board, boardUserResponseDtos, sectionResponseDtos);
    }

    public List<BoardSectionResponseDto> findAllBoardTest(User user) {
        List<BoardSectionResponseDto> boardSectionResponseDtos = new ArrayList<>();

        List<BoardUser> boardUsers = jpaQueryFactory
            .selectFrom(qBoardUser)
            .leftJoin(qBoard).on(qBoard.boardId.eq(qBoardUser.board.boardId))
            .fetchJoin()
            .where(qBoardUser.user.id.eq(user.getId()))
            .fetch();

        for (BoardUser boardUser : boardUsers) {
            Board board = boardUser.getBoard();

            List<SectionResponseDto> sectionResponseDtos = jpaQueryFactory
                .select(Projections.constructor(SectionResponseDto.class, qSection))
                .from(qSection)
                .where(qSection.board.boardId.eq(board.getBoardId()))
                .fetch();

            List<BoardUserResponseDto> boardUserResponseDtos = jpaQueryFactory
                .select(Projections.constructor(BoardUserResponseDto.class, qBoardUser))
                .from(qBoardUser)
                .where(qBoardUser.board.eq(board))
                .fetch();

            boardSectionResponseDtos.add(
                new BoardSectionResponseDto(board, boardUserResponseDtos, sectionResponseDtos));
        }

        return boardSectionResponseDtos;
    }
}
