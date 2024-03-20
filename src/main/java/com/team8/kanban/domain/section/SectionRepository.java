package com.team8.kanban.domain.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByNext(Long next);

    @Query("SELECT s FROM Section s WHERE s.id NOT IN (SELECT sec.next FROM Section sec WHERE sec.next IS NOT NULL)")
    Section findHeadEntities();

    @Query("SELECT s FROM Section s WHERE s.next IS NULL")
    Section findLastSection();

    boolean existsByBoard_BoardIdAndId(Long boardId, Long sectionId);
}
