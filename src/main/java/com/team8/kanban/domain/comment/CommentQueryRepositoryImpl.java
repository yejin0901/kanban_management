package com.team8.kanban.domain.comment;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team8.kanban.global.config.JpaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team8.kanban.domain.comment.QComment.comment;


@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentRepositoryCustom{

    private final JpaConfig jpaConfig;

    public Page<Comment> findAllPage(Pageable pageable){
        JPAQueryFactory queryFactory = jpaConfig.jpaQueryFactory();

        List<Comment> comments = queryFactory.
                selectFrom(comment)
                .orderBy(comment.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(comments, pageable, comments.size());
    }

}
