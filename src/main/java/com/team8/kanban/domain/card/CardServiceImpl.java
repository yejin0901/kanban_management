package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.exception.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public CommonResponse<CardResponse> createCard(User user, CreateCardRequest request) {

        Card newCard = toEntity(request);
        cardRepository.save(newCard);

        return new CommonResponse<>("Card 입력 완료", toResponse(newCard));
    }

    @Override
    public CommonResponse<CardResponse> updateCard(User user, UpdateCardRequest request, Long cardId) {
        Card updateCard = findCard(cardId);
        checkOwnerCard(user.getId(), cardId);

        updateCard.update(request);
        cardRepository.save(updateCard);

        return new CommonResponse<>("Card 수정 완료", toResponse(updateCard));

    }

    @Override
    public CommonResponse<Boolean> deleteCard(User user, Long cardId) {
        Card deleteCard = findCard(cardId);
        checkOwnerCard(user.getId(), cardId);

        cardRepository.delete(deleteCard);
        return new CommonResponse<>("Card 삭제 완료", true);
    }


    private Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 카드가 없습니다."));
    }

    private void checkOwnerCard(Long userId, Long cardId) {
        if (!cardId.equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    private CardResponse toResponse(Card card) {
        return CardResponse.builder()
                .cardId(card.getCardId())
                .cardName(card.getCardName())
                .description(card.getDescription())
                .expiredDate(card.getExpiredDate().atStartOfDay())
                .createdAt(card.getCreatedAt())
                .modifiedAt(card.getModifiedAt())
                .build();
    }

    private Card toEntity(CreateCardRequest request) {
        return Card.builder()
                .cardName(request.getCardName())
                .description(request.getDescription())
                .expiredDate(request.getExpiredDate())
                .build();
    }
}
