package com.team8.kanban.domain.section;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto requestDto);

    void deleteSection(Long sectionId);

    SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto);

    List<SectionResponseDto> getAllSection();
}
