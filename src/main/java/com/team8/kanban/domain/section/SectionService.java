package com.team8.kanban.domain.section;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto requestDto, Long boardId);

    void deleteSection(Long sectionId, Long boardId);

    List<SectionCardResponseDto> sortSection(Long boardId);
    SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto, Long boardId);

    List<SectionCardResponseDto> updateNextpos(Long selectedSectionId, Long changeSectionId, Long boardId);
}
