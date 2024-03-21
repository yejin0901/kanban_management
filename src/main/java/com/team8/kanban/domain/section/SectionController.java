package com.team8.kanban.domain.section;

import com.team8.kanban.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class SectionController {

    private final SectionService sectionServiceImpl;

    @PostMapping("/{boardId}/sections")
    public ResponseEntity<CommonResponse<SectionResponseDto>> createSection(
            @PathVariable Long boardId,
            @RequestBody SectionRequestDto requestDto
    ) {
        SectionResponseDto response = sectionServiceImpl.createSection(requestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 생성되었습니다.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{boardId}/sections")
    public ResponseEntity<CommonResponse<List<SectionCardResponseDto>>> getAllSection(
            @PathVariable Long boardId
    ) {
        List<SectionCardResponseDto> response = sectionServiceImpl.sortSection(boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionCardResponseDto>>builder()
                        .msg("섹션이 조회되었습니다.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{boardId}/sections/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> deleteSection(
            @PathVariable Long boardId,
            @PathVariable Long sectionId

    ) {
        sectionServiceImpl.deleteSection(sectionId, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 삭제되었습니다.")
                        .build());

    }

    @PatchMapping("/{boardId}/sections/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> updateSection(
            @PathVariable Long boardId,
            @PathVariable Long sectionId,
            @RequestBody SectionRequestDto requestDto

    ) {
        SectionResponseDto response = sectionServiceImpl.updateSection(sectionId, requestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 수정되었습니다.")
                        .data(response)
                        .build());

    }

    @PostMapping("/{boardId}/sections/position")
    public ResponseEntity<CommonResponse<List<SectionCardResponseDto>>> updatePos(
            @PathVariable Long boardId,
            @RequestParam Long selectedSectionId,
            @RequestParam Long changeSectionId
    ) {
        List<SectionCardResponseDto> response = sectionServiceImpl.updateNextpos(selectedSectionId, changeSectionId, boardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionCardResponseDto>>builder()
                        .msg("섹션 위치가 변경었습니다.")
                        .data(response)
                        .build());

    }

    @GetMapping("/sections/get")
    public ResponseEntity<CommonResponse<List<SectionCardResponseDto>>> get(
    ) {
        List<SectionCardResponseDto> response = sectionServiceImpl.getc();
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionCardResponseDto>>builder()
                        .msg("섹션 위치가 변경었습니다.")
                        .data(response)
                        .build());

    }
}
