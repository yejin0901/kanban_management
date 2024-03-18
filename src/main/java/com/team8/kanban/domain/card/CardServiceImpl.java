package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.entity.ColorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public List<CardResponse> getCards() {
        return cardRepository.findCards();
    }

    @Override
    public CardResponse getCard(Long cardId) {
        return cardRepository.findCard(cardId);
    }

    @Override
    @Transactional
    public CardResponse createCard(User user, CreateCardRequest request) {
        Card newCard = toEntity(request, user);
        cardRepository.save(newCard);

        return toResponse(newCard);
    }

    @Override
    @Transactional
    public CardResponse updateCard(User user, UpdateCardRequest request, Long cardId) {
        Card updateCard = findCard(cardId);
        checkOwnerCard(user.getId(), updateCard.getUserid());

        updateCard.update(request);
        cardRepository.save(updateCard);

        return toResponse(updateCard);
    }

    @Override
    @Transactional
    public Boolean deleteCard(User user, Long cardId) {
        Card deleteCard = findCard(cardId);
        checkOwnerCard(user.getId(), deleteCard.getUserid());

        cardRepository.delete(deleteCard);
        return true;
    }


    private Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 카드가 없습니다."));
    }

    private void checkOwnerCard(Long userId, Long cardByUserId) {
        if (!cardByUserId.equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    private CardResponse toResponse(Card card) {
        return CardResponse.builder()
                .cardId(card.getCardId())
                .cardName(card.getCardName())
                .description(card.getDescription())
                .username(card.getUsername())
                .expiredDate(card.getExpiredDate())
                .createdAt(card.getCreatedAt())
                .modifiedAt(card.getModifiedAt())
                .build();
    }

    private Card toEntity(CreateCardRequest request, User user) {
        return Card.builder()
                .cardName(request.getCardName())
                .description(request.getDescription())
                .userid(user.getId())
                .username(user.getUsername())
                .expiredDate(request.getExpiredDate())
                .colorEnum(ColorEnum.valueOf(request.getColor()))
                .build();
    }
}
