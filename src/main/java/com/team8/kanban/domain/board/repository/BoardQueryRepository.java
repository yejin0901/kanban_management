package com.team8.kanban.domain.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team8.kanban.domain.board.dto.BoardSectionResponseDto;
import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.section.QSection;
import com.team8.kanban.domain.section.SectionResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private QSection qSection = QSection.section;

    public BoardSectionResponseDto findBoard(Board board) {

        List<SectionResponseDto> sectionResponseDtos = jpaQueryFactory
            .select(Projections.constructor(SectionResponseDto.class, qSection))
            .from(qSection)
            .where(qSection.board.eq(board))
            .fetch();

        return new BoardSectionResponseDto(board, sectionResponseDtos);
    }

//    public List<BoardSectionResponseDto> findAllBoards(User user) {
//        List<BoardSectionResponseDto> boardSectionResponseDtos = new ArrayList<>();
//
//        List<BoardUser> boardUsers = jpaQueryFactory
//            .selectFrom(qBoardUser)
//            .leftJoin(qBoard).on(qBoard.boardId.eq(qBoardUser.board.boardId))
//            .fetchJoin()
//            .where(qBoardUser.user.id.eq(user.getId()))
//            .fetch();
//
//        for (BoardUser boardUser : boardUsers) {
//            Board board = boardUser.getBoard();
//
//            List<SectionResponseDto> sectionResponseDtos = jpaQueryFactory
//                .select(Projections.constructor(SectionResponseDto.class, qSection))
//                .from(qSection)
//                .where(qSection.board.boardId.eq(board.getBoardId()))
//                .fetch();
//
//            List<BoardUserResponseDto> boardUserResponseDtos = jpaQueryFactory
//                .select(Projections.constructor(BoardUserResponseDto.class, qBoardUser))
//                .from(qBoardUser)
//                .where(qBoardUser.board.eq(board))
//                .fetch();
//
//            boardSectionResponseDtos.add(
//                new BoardSectionResponseDto(board, boardUserResponseDtos, sectionResponseDtos));
//        }
//
//        return boardSectionResponseDtos;
//    }
}

