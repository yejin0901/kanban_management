package com.team8.kanban.domain.comment.repository;

import com.team8.kanban.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> ,CommentRepositoryCustom{

    @Query("SELECT comment from Comment comment where comment.card.cardId = :cardId")
    List<Comment> findAllByCardIdV1(Long cardId);

    @Query("select c from Comment c left join fetch c.user where c.card.cardId =:cardId")
    List<Comment> findAllByCardIdV3(Long cardId);

    @Query("SELECT comment from Comment comment where comment.card.cardId = :cardId")
    Page<Comment> findAllByCardIdV4(Long cardId,Pageable pageable);
    @Query(value = "select c from Comment c left join fetch c.user where c.card.cardId =:cardId"
    ,countQuery = "select count(comment) from Comment comment")
    Page<Comment> findAllByCardIdV5(Long cardId,Pageable pageable);

    @Query("select c from Comment c left join fetch c.user where c.card.cardId =:cardId")
    Slice<Comment> findAllByCardIdV6(Long cardId, Pageable pageable);

    List<Comment> findCommentsByCard_CardId(Long cardId);
}
