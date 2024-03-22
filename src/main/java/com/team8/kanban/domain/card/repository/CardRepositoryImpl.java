package com.team8.kanban.domain.card.repository;


import com.querydsl.core.types.Projections;
import com.team8.kanban.domain.card.dto.CardCommentResponse;
import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CommentByCardResponse;
import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.card.entity.QCard;
import com.team8.kanban.domain.comment.QComment;
import com.team8.kanban.domain.user.QUser;
import com.team8.kanban.global.config.JpaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

    private final JpaConfig jpaConfig;
    private QCard qCard = QCard.card;
    private QUser qUser = QUser.user;
    private QComment qComment = QComment.comment;


    @Override
    public CardCommentResponse findCard(Long cardId) {
        CardResponse card = jpaConfig.jpaQueryFactory()
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
                .leftJoin(qUser).on(qUser.id.eq(qCard.userid))
                .where(qCard.cardId.eq(cardId))
                .fetchOne();

        List<CommentByCardResponse> commentByCardResponses = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CommentByCardResponse.class,
                        qComment.id.as("commentId"),
                        qComment.card.cardId,
                        qComment.user.username.as("commentUsername"),
                        qComment.content.as("commentContent")))
                .from(qComment)
                .orderBy(qComment.id.asc())
                .where(qComment.card.cardId.in(card.getCardId()))
                .fetch();

        return new CardCommentResponse(card, commentByCardResponses);
    }

    @Override
    public List<CardCommentResponse> findCardsV1(Long sectionId) {
        List<CardCommentResponse> cardCommentResponses = new ArrayList<>();
        List<CardResponse> cardResponses = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CardResponse.class,
                        qCard.cardId,
                        qCard.cardName,
                        qCard.description,
                        qUser.username,
                        qCard.expiredDate,
                        qCard.createdAt,
                        qCard.modifiedAt,
                        qCard.sectionId,
                        qCard.position
                ))
                .from(qCard)
                .leftJoin(qUser).on(qUser.id.eq(qCard.userid))
                .orderBy(qCard.position.asc().nullsLast(), qCard.cardId.desc())
                .where(qCard.sectionId.eq(sectionId))
                .fetch();

        List<Long> cardIds = cardResponses.stream().map(CardResponse::getCardId).toList();

        List<CommentByCardResponse> commentByCardResponses = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CommentByCardResponse.class,
                        qComment.id.as("commentId"),
                        qComment.card.cardId,
                        qComment.user.username.as("commentUsername"),
                        qComment.content.as("commentContent")))
                .from(qComment)
                .orderBy(qComment.id.asc())
                .where(qComment.card.cardId.in(cardIds))
                .fetch();
        for (CardResponse cardResponse : cardResponses) {
            List<CommentByCardResponse> addCardList = new ArrayList<>();
            //섹션에 있는 카드에 달려있는 모든 댓글 돌림
            for (CommentByCardResponse commentByCardResponse : commentByCardResponses) {
                //카드 번호와 댓글에 있는 카드번호가 같으면 위에서 만든 리스트에 입력
                if (cardResponse.getCardId() == commentByCardResponse.getCardId()) {
                    addCardList.add(commentByCardResponse);
                }
            }
            cardCommentResponses.add(new CardCommentResponse(cardResponse, addCardList));
        }
        return cardCommentResponses;
    }

    @Override
    public Slice<CardCommentResponse> findCards(Long sectionId, Pageable pageable) {
        List<CardCommentResponse> cardCommentResponses = new ArrayList<>();
        List<CardResponse> cardResponses = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CardResponse.class,
                        qCard.cardId,
                        qCard.cardName,
                        qCard.description,
                        qUser.username,
                        qCard.expiredDate,
                        qCard.createdAt,
                        qCard.modifiedAt,
                        qCard.sectionId,
                        qCard.position
                ))
                .from(qCard)
                .leftJoin(qUser).on(qUser.id.lt(qCard.userid))
                .orderBy(qCard.position.asc().nullsLast(), qCard.cardId.desc())
                .where(qCard.sectionId.eq(sectionId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (cardResponses.size() > pageable.getPageSize()) {
            cardResponses.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<Long> cardIds = cardResponses.stream().map(CardResponse::getCardId).toList();

        List<CommentByCardResponse> commentByCardResponses = jpaConfig.jpaQueryFactory()
                .select(Projections.fields(CommentByCardResponse.class,
                        qComment.id.as("commentId"),
                        qComment.card.cardId,
                        qComment.user.username.as("commentUsername"),
                        qComment.content.as("commentContent")))
                .from(qComment)
                .orderBy(qComment.id.asc())
                .where(qComment.card.cardId.in(cardIds))
                .fetch();
        for (CardResponse cardResponse : cardResponses) {
            List<CommentByCardResponse> addCardList = new ArrayList<>();
            //섹션에 있는 카드에 달려있는 모든 댓글 돌림
            for (CommentByCardResponse commentByCardResponse : commentByCardResponses) {
                //카드 번호와 댓글에 있는 카드번호가 같으면 위에서 만든 리스트에 입력
                if (cardResponse.getCardId() == commentByCardResponse.getCardId()) {
                    addCardList.add(commentByCardResponse);
                }
            }
            cardCommentResponses.add(new CardCommentResponse(cardResponse, addCardList));
        }
        return new SliceImpl<>(cardCommentResponses, pageable, hasNext);
    }

    @Override
    public List<Card> findCardsBySectionId(Long sectionId) {
        return jpaConfig.jpaQueryFactory()
                .selectFrom(qCard)
                .leftJoin(qUser).on(qCard.userid.eq(qUser.id))
                .orderBy(qCard.cardId.asc())
                .where(qCard.sectionId.eq(sectionId))
                .fetch();
    }
}
