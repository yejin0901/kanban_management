package com.team8.kanban.domain.comment;

import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentResponse create(User user, CommentRequest commentRequest, Long cardId);
    CommentResponse update(User user, Long id,CommentRequest updateCommentRequest, Long cardId);
    CommentResponse delete(User user,Long id, Long cardId);
    CommentResponse getComment(Long id);
    List<CommentResponse> getCommentsV1(Long cardId);
    List<CommentResponse> getCommentsV2(Long cardId);
    List<CommentResponse> getCommentsV3(Long cardId);
    Page<CommentResponse> getCommentsV4(Long cardId, Pageable pageable);
    Page<CommentResponse> getCommentsV5(Long cardId, Pageable pageable);
    Slice<CommentResponse> getCommentsV6(Long cardId, Pageable pageable);
    List<CommentResponse> getComment(Long cardId, CommentRequest commentRequest);
}
