package com.team8.kanban.domain.board.repository;

import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.board.entity.BoardUser;
import com.team8.kanban.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

    boolean existsByBoardAndUser (Board board, User user);

    List<BoardUser> findAllByUserId(Long userId);
}
