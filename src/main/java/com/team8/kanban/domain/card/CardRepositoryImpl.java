package com.team8.kanban.domain.card;


import com.querydsl.core.types.Projections;
import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.user.QUser;
import com.team8.kanban.global.config.JpaConfig;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

    private final JpaConfig jpaConfig;
    private QCard qCard = QCard.card;
    private QUser qUser = QUser.user;


    @Override
    public CardResponse findCard(Long cardId) {
        CardResponse card = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CardResponse.class,
                        qCard.cardId,
                        qCard.cardName,
                        qCard.description,
                        qUser.username,
                        qCard.expiredDate,
                        qCard.createdAt,
                        qCard.modifiedAt))
                .from(qCard)
                .leftJoin(qUser).on(qUser.id.eq(qCard.userid))
                .where(qCard.cardId.eq(cardId))
                .fetchOne();

        if (Objects.isNull(card)) {
            throw new IllegalArgumentException("해당하는 카드가 없습니다.");
        }
        return card;
    }

    @Override
    public List<CardResponse> findCards(Long sectionId) {
        return jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CardResponse.class,
                        qCard.cardId,
                        qCard.cardName,
                        qCard.description,
                        qUser.username,
                        qCard.expiredDate,
                        qCard.createdAt,
                        qCard.modifiedAt,
                        qCard.sectionId,
                        qCard.position))
                .from(qCard)
                .leftJoin(qUser).on(qCard.userid.eq(qUser.id))
                .orderBy(qCard.position.asc().nullsLast(),qCard.cardId.desc())
                .where(qCard.sectionId.eq(sectionId))
                .fetch();
    }
}
