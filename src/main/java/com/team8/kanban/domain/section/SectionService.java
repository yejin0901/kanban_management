package com.team8.kanban.domain.section;

import com.team8.kanban.global.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto requestDto, Long boardId);

    void deleteSection(Long sectionId, Long boardId);

    List<SectionResponseDto> sortSection(Long boardId);
    SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto, Long boardId);

    List<SectionResponseDto> updateNextpos(Long selectedSectionId,Long changeSectionId, Long boardId);
}
