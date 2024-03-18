package com.team8.kanban.domain.section;


import com.team8.kanban.global.exception.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class SectionController {

    private final SectionService sectionServiceImpl;

    @PostMapping("/sections")
    public ResponseEntity<CommonResponse<SectionResponseDto>> createSection(
            @RequestBody SectionRequestDto requestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SectionResponseDto response = sectionServiceImpl.createSection(requestDto);
//        SectionResponseDto response = sectionServiceImpl.createSchedule(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 생성되었습니다.")
                        .data(response)
                        .build());

    }

    @PostMapping("/sections")
    public ResponseEntity<CommonResponse<List<SectionResponseDto>>> getAllSection(
            @RequestBody SectionRequestDto requestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SectionResponseDto> response = sectionServiceImpl.getAllSection();
//        SectionResponseDto response = sectionServiceImpl.createSchedule(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionResponseDto>>builder()
                        .msg("섹션이 생성되었습니다.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> deleteSection(
            @PathVariable Long sectionId,
            @RequestBody SectionRequestDto requestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        sectionServiceImpl.deleteSection(sectionId);
//        SectionResponseDto response = sectionServiceImpl.deleteSchedule(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 삭제되었습니다.")
                        .build());

    }

    @PatchMapping("/section/{sectionId}")
    public ResponseEntity<CommonResponse<SectionResponseDto>> updateSection(
            @PathVariable Long sectionId,
            @RequestBody SectionRequestDto requestDto
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        SectionResponseDto response = sectionServiceImpl.updateSection(sectionId, requestDto);
//        SectionResponseDto response = sectionServiceImpl.updateSection(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<SectionResponseDto>builder()
                        .msg("섹션이 수정되었습니다.")
                        .data(response)
                        .build());

    }

    @PostMapping("/section/{sectionId}")
    public ResponseEntity<CommonResponse<List<SectionResponseDto>>> updatePos(
            @PathVariable Long sectionId,
            @RequestParam Integer pos,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<SectionResponseDto> response = sectionServiceImpl.updatePos(sectionId, pos);
//        SectionResponseDto response = sectionServiceImpl.updatePos(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<SectionResponseDto>>builder()
                        .msg("섹션 위치가 변경었습니다.")
                        .data(response)
                        .build());

    }
}
