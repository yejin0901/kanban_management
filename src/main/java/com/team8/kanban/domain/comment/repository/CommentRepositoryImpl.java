package com.team8.kanban.domain.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team8.kanban.domain.comment.Comment;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.global.config.JpaConfig;
import lombok.RequiredArgsConstructor;


import java.util.List;

import static com.team8.kanban.domain.comment.QComment.comment;
import static com.team8.kanban.domain.user.QUser.user;


@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JpaConfig jpaConfig;


    @Override
    public List<CommentResponse> V2findAllByCardId(Long cardId) {
//        JPAQueryFactory queryFactory = jpaConfig.jpaQueryFactory();
        List<CommentResponse> result = jpaConfig.jpaQueryFactory()
                .select(
                        Projections.fields(CommentResponse.class,
                        comment.id,
                        comment.content,
                        comment.user.username.as("writer"),
                        user.username.as("writer"),
                        comment.createdAt,
                        comment.modifiedAt
                ))
                .from(comment)
                .where(comment.card.cardId.eq(cardId))
                .fetch();
        return result;
    }

    public List<Comment> findByContent(Long cardId,String content){
        JPAQueryFactory queryFactory = jpaConfig.jpaQueryFactory();
        return queryFactory
                .select(comment)
                .from(comment)
                .join(comment.user,user).fetchJoin()
                .where(comment.content.eq(content).and(comment.card.cardId.eq(cardId)))
                .fetch();
    }
    public List<Comment> findAllV2(){
        JPAQueryFactory queryFactory = jpaConfig.jpaQueryFactory();
        return queryFactory
                .select(comment)
                .from(comment)
                .join(comment.user, user).fetchJoin()
                .fetch();
    }
    public List<CommentResponse> findAllV3() {
        List<CommentResponse> result = jpaConfig.jpaQueryFactory()
                .select(
                        Projections.fields(CommentResponse.class,
                                comment.id,
                                comment.content,
                                comment.user.username.as("writer"),
                                user.username.as("writer"),
                                comment.createdAt,
                                comment.modifiedAt
                        ))
                .from(comment)
                .fetch();
        return result;
    }
}
