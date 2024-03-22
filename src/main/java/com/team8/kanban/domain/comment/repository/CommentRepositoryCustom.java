package com.team8.kanban.domain.comment.repository;


import com.team8.kanban.domain.comment.Comment;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentRepositoryCustom {
//    Page<Comment> findAllPageV2(Pageable pageable);
    List<CommentResponse> V2findAllByCardId(Long cardId);
}
