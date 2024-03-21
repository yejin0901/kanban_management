package com.team8.kanban.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentsByCard_CardId(Long cardId);
}
