package com.team8.kanban.domain.section;

import java.util.List;

public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto requestDto);

    void deleteSection(Long sectionId);

    SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto);

    List<SectionResponseDto> updatePos(Long sectionId, Integer pos);

    List<SectionResponseDto> getAllSection();
}
