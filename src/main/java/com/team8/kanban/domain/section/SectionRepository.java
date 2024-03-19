package com.team8.kanban.domain.section;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByPrev(Integer prev);
}
