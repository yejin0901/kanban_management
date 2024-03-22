package com.team8.kanban.domain.card.controller;

import com.team8.kanban.domain.card.dto.*;
import com.team8.kanban.domain.card.service.CardService;
import com.team8.kanban.global.common.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    //카드 조회
    @GetMapping("/v1")
    public ResponseEntity<CommonResponse<List<CardCommentResponse>>> getCards(
            @Valid @RequestBody SectionIdCardRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CardCommentResponse>>builder()
                        .msg("조회 되었습니다.")
                        .data(cardService.getCardsV1(request.getSectionId()))
                        .build());
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Slice<CardCommentResponse>>> getCardsV2(
            @Valid @RequestBody SectionIdCardRequest request,
            @PageableDefault(sort = "position", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Slice<CardCommentResponse>>builder()
                        .msg("조회 되었습니다.")
                        .data(cardService.getCards(request.getSectionId(), pageable))
                        .build());
    }

    //카드 개별조회
    @GetMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardCommentResponse>> getCard(
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CardCommentResponse>builder()
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
            @RequestBody UpdateCardRequest request,
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
    @PostMapping("/position")
    public ResponseEntity<CommonResponse<Boolean>> changePosition(
            @Valid @RequestBody PositionChangeRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Boolean>builder()
                        .msg("position이 변경되었습니다.")
                        .data(cardService.changePosition(request.getSectionId(), request.getPositionSet()))
                        .build());
    }

    //SectionId 변경
    @PostMapping("/section")
    public ResponseEntity<CommonResponse<Boolean>> changeSection(
            @Valid @RequestBody SectionChangeRequest request) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Boolean>builder()
                        .msg("section이 변경되었습니다.")
                        .data(cardService.changeSection(request.getCardId(),
                                request.getNewSectionId(),
                                request.getNewSectionIdSet(),
                                request.getCardPositionId()))
                        .build());
    }

    //카드에 유저 추가
    @PostMapping("/{cardId}/user")
    public ResponseEntity<CommonResponse<Boolean>> addUserByCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserInCardRequest request,
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Boolean>builder()
                        .msg("추가 되었습니다.")
                        .data(cardService.addUserByCard(userDetails.getUser(), request.getUserId(), cardId))
                        .build());
    }

    //카드에 유저 삭제
    @DeleteMapping("/{cardId}/user")
    public ResponseEntity<CommonResponse<Boolean>> deleteUserByCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserInCardRequest request,
            @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Boolean>builder()
                        .msg("삭제 되었습니다.")
                        .data(cardService.deleteUserByCard(userDetails.getUser(), request.getUserId(), cardId))
                        .build());
    }
}
