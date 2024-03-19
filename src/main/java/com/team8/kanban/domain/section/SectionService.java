package com.team8.kanban.domain.section;

import com.team8.kanban.global.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto requestDto);

    void deleteSection(Long sectionId);

    SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto);

    List<SectionResponseDto> getAllSection();

    List<SectionResponseDto> updateNextpos(Long selectedSectionId,Long changeSectionId);
}
