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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

    private final JpaConfig jpaConfig;
    private QCard qCard = QCard.card;
    private QUser qUser = QUser.user;
    private QComment qComment = QComment.comment;


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


    public List<CardResponse> findCards1(Long sectionId) {
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
                .orderBy(qCard.position.asc().nullsLast(), qCard.cardId.desc())
                .where(qCard.sectionId.eq(sectionId))
                .fetch();
    }

    @Override
    public List<CardResponse> findCards(Long sectionId) {
        return jpaConfig.jpaQueryFactory()
                .select(Projections.constructor(CardResponse.class,
                        qCard.cardId,
                        qCard.cardName,
                        qCard.description,
                        qUser.username,
                        qCard.expiredDate,
                        qCard.createdAt,
                        qCard.modifiedAt,
                        qCard.sectionId,
                        qCard.position,
                        Projections.list(
                                Projections.fields(CommentByCardResponse.class,
                                        qComment.content,
                                        qComment.id,
                                        qCard.cardId,
                                        qComment.user.username
                                ).as("comments")
                        )
                ))
                .from(qCard)
                .leftJoin(qUser).on(qUser.id.eq(qCard.userid))
                .leftJoin(qComment).on(qComment.card.eq(qCard))
                .orderBy(qCard.position.asc().nullsLast(), qCard.cardId.desc())
                .where(qCard.sectionId.eq(sectionId))
                .fetch();
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


    @Override
    public List<CardCommentResponse> cardComment(Long sectionId) {

        List<CardCommentResponse> cardCommentResponses = new ArrayList<>();
        //1 섹션id에 해당하는 카드리스트를 찾아오고 List<CardResponse>에 저장
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

        //2. CardResponse에 있는 CardId를 List<Long> 받아와서 저장
        List<Long> cardIds = cardResponses.stream().map(CardResponse::getCardId).toList();

        //3. List<Long>을 이용해서 조회된 Card에 달린 댓글을 List<CommentByCardResponse>에 저장
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
        //4. 조립
        //섹션에 해당하는 카드수만큼 반복
        for (CardResponse cardResponse : cardResponses) {
            List<CommentByCardResponse> addCardList = new ArrayList<>();
            //섹션에 있는 카드에 달려있는 모든 댓글 돌림
            for (CommentByCardResponse commentByCardResponse : commentByCardResponses) {
                //카드 번호와 댓글에 있는 카드번호가 같으면 위에서 만든 리스트에 입력
                if (cardResponse.getCardId() == commentByCardResponse.getCardId()) {
                    addCardList.add(commentByCardResponse);
//                    commentByCardResponses.remove(commentByCardResponse);
                }
            }
            cardCommentResponses.add(new CardCommentResponse(cardResponse, addCardList));
        }

        return cardCommentResponses;
    }

}
