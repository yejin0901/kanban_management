package com.team8.kanban.domain.card;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.entity.ColorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> getCards(Long sectionId) {
        return cardRepository.findCards(sectionId);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public List<CardResponse> changePosition(Long sectionId, String cardIdSet) {
        long[] idByCard = Arrays.stream(cardIdSet.split("_"))
                .mapToLong(Long::parseLong)
                .toArray();
        List<Card> positionUpdateByCards = cardRepository.findCardsBySectionId(sectionId);

        //idByCard: 13 3 2 1 14 12
        for (int i = 0; i < idByCard.length; i++) {
            //positionUpdateByCard: 1 2 3 14 13 12
            for (int j = 0; j < positionUpdateByCards.size(); j++) {
                if (idByCard[i] == positionUpdateByCards.get(j).getCardId()) {
                    positionUpdateByCards.get(j).setPosition(i);
                    continue;
                }
            }
        }
        cardRepository.saveAll(positionUpdateByCards);
        return cardRepository.findCards(sectionId);
    }

    @Override
    @Transactional
    public List<CardResponse> changeSection(Long cardId, Long newSectionId, String newSectionIdSet, Long cardPosition) {
        Card sectionUpdateCard = findCard(cardId);
        sectionUpdateCard.setSection(newSectionId);
        cardRepository.save(sectionUpdateCard);

        //cardId 10, cardPositionId 2 ,newSectionIdSet 9_8_7_6_5_4 -> 9_8_10_7_6_5_4
        long[] idByCard = Arrays.stream(newSectionIdSet.split("_"))
                .mapToLong(Long::parseLong)
                .toArray();

        /////////////////////

        long[] idByCardAddPositionId = new long[idByCard.length + 1];
        for (int i = 0; i < cardPosition; i++) {
            idByCardAddPositionId[i] = idByCard[i];
        }
        idByCardAddPositionId[Math.toIntExact(cardPosition)] = cardId;
        for (int i = (int) (cardPosition + 1); i < idByCardAddPositionId.length; i++) {
            idByCardAddPositionId[i] = idByCard[i - 1];
        }

        List<Card> positionUpdateByCards = cardRepository.findCardsBySectionId(newSectionId);

        for (int i = 0; i < idByCardAddPositionId.length; i++) {
            //positionUpdateByCard: 1 2 3 14 13 12
            for (int j = 0; j < positionUpdateByCards.size(); j++) {
                if (idByCardAddPositionId[i] == positionUpdateByCards.get(j).getCardId()) {
                    positionUpdateByCards.get(j).setPosition(i);
                    continue;
                }
            }
        }
        cardRepository.saveAll(positionUpdateByCards);
        return cardRepository.findCards(newSectionId);
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
                .sectionId(card.getSectionId())
                .position(card.getPosition())
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
                .sectionId(request.getSectionId())
                .build();
    }
}