package com.team8.kanban.domain.board.repository;

import com.team8.kanban.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
