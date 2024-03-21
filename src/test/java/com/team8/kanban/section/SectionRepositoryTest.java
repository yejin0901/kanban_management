package com.team8.kanban.section;

import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.section.Section;
import com.team8.kanban.domain.section.SectionRepository;
import com.team8.kanban.global.config.JpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
public class SectionRepositoryTest {

    @Autowired
    SectionRepository sectionRepository;

    Board board1;
    Board board2;
    Section section1;
    Section section2;
    Section section3;

    void setup() {
    }

}
