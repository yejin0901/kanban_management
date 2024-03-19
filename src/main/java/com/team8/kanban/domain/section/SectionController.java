package com.team8.kanban.domain.section;

import com.team8.kanban.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/sections")
public class SectionController {

    private final SectionService sectionServiceImpl;

    @PostMapping
    public ResponseEntity<CommonResponse<SectionResponseDto>> createSection(
            @RequestBody SectionRequestDto requestDto
    ) {
        SectionResponseDto response = sectionServiceImpl.createSection(requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 생성되었습니다.")
                        .data(response)
                        .build());

    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SectionResponseDto>>> getAllSection() {
        List<SectionResponseDto> response = sectionServiceImpl.getAllSection();
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionResponseDto>>builder()
                        .msg("섹션이 조회되었습니다.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> deleteSection(
            @PathVariable Long sectionId

    ) {
        sectionServiceImpl.deleteSection(sectionId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 삭제되었습니다.")
                        .build());

    }

    @PatchMapping("/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> updateSection(
            @PathVariable Long sectionId,
            @RequestBody SectionRequestDto requestDto

    ) {
        SectionResponseDto response = sectionServiceImpl.updateSection(sectionId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 수정되었습니다.")
                        .data(response)
                        .build());

    }

    @PostMapping("/position")
    public ResponseEntity<CommonResponse<List<SectionResponseDto>>> updatePos(
            @RequestParam Long selectedSectionId,
            @RequestParam Long changeSectionId
    ) {
        List<SectionResponseDto> response = sectionServiceImpl.updateNextpos(selectedSectionId, changeSectionId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionResponseDto>>builder()
                        .msg("섹션 위치가 변경었습니다.")
                        .data(response)
                        .build());

    }
}
