package com.team8.kanban.domain.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long>, SectionRepositoryQuery{
    Optional<Section> findByNext(Long next);

    @Query("SELECT s FROM Section s WHERE s.id NOT IN (SELECT sec.next FROM Section sec WHERE sec.next IS NOT NULL) AND s.board.boardId = :boardId")
    Section findHeadEntities(@Param("boardId") Long boardId);

    @Query("SELECT s FROM Section s WHERE s.next IS NULL")
    Section findLastSection();

    boolean existsByBoard_BoardIdAndId(Long boardId, Long sectionId);
}
