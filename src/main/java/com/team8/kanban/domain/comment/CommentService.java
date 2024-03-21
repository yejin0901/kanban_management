package com.team8.kanban.domain.comment;

import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentResponse create(User user, CommentRequest commentRequest, Long cardId);
    CommentResponse update(User user, Long id,CommentRequest updateCommentRequest, Long cardId);
    CommentResponse delete(User user,Long id, Long cardId);
    CommentResponse getComment(Long id);
}
