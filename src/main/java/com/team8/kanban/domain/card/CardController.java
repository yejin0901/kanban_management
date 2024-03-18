package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.global.exception.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    //카드 조회


    //카드 개별조회

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
}
