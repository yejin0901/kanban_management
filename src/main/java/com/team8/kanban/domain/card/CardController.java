package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.*;
import com.team8.kanban.global.exception.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    //카드 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<CardResponse>>> getCards(
            @Valid @RequestBody SectionIdCardRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CardResponse>>builder()
                        .msg("조회 되었습니다.")
                        .data(cardService.getCards(request.getSectionId()))
                        .build());
    }

    //카드 개별조회
    @GetMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardResponse>> getCard(
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CardResponse>builder()
                        .msg("조회 되었습니다.")
                        .data(cardService.getCard(cardId))
                        .build());
    }


    //카드 입력
    @PostMapping
    public ResponseEntity<CommonResponse<CardResponse>> createCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCardRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CardResponse>builder()
                        .msg("Card 생성 성공")
                        .data(cardService.createCard(userDetails.getUser(), request))
                        .build());
    }

    //카드 수정
    @PatchMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardResponse>> updateCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdateCardRequest request,
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CardResponse>builder()
                        .msg("Card 수정 성공")
                        .data(cardService.updateCard(userDetails.getUser(), request, cardId))
                        .build());
    }

    //카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponse<Boolean>> deleteCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(CommonResponse.<Boolean>builder()
                        .msg("Card 삭제 성공")
                        .data(cardService.deleteCard(userDetails.getUser(), cardId))
                        .build());
    }

    //position 변경
    @PostMapping("/change-position")
    public ResponseEntity<CommonResponse<List<CardResponse>>> changePosition(
            @Valid @RequestBody PositionChangeRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CardResponse>>builder()
                        .msg("position이 변경되었습니다.")
                        .data(cardService.changePosition(request.getSectionId(), request.getPositionSet()))
                        .build());
    }

    //SectionId 변경
    @PostMapping("/change-section")
    public ResponseEntity<CommonResponse<List<CardResponse>>> changeSection(
            @Valid @RequestBody SectionChangeRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CardResponse>>builder()
                        .msg("section이 변경되었습니다.")
                        .data(cardService.changeSection(request.getCardId(),
                                request.getNewSectionId(),
                                request.getNewSectionIdSet(),
                                request.getCardPositionId()))
                        .build());
    }
}
