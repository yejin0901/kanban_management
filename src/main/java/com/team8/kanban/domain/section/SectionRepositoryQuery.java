package com.team8.kanban.domain.section;

import java.util.List;

public interface SectionRepositoryQuery {
    List<SectionCardResponseDto> findSectionAndCard(List<SectionResponseDto> sectionList);
}
