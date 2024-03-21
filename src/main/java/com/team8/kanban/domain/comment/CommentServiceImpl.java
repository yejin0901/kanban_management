package com.team8.kanban.domain.comment;


import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.card.repository.CardRepository;
import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    //card_id 넣어야 됨
    public CommentResponse create(User user, CommentRequest commentRequest , Long cardId) {

        Card findCard = cardRepository.findById(cardId).orElseThrow();

        Comment createComment = new Comment(commentRequest.getContent(), user, findCard);
        Comment savedComment = commentRepository.save(createComment);
        return new CommentResponse(savedComment,user);
    }

    public CommentResponse update(User user,Long id,CommentRequest updateCommentRequest, Long cardId) {

        Comment findComment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if(!(Objects.equals(findComment.getUser().getId(), user.getId()))){
            throw new IllegalArgumentException("자신의 댓글만 수정 가능합니다.");
        }
        findComment.updateContent(updateCommentRequest.getContent());
        return new CommentResponse(findComment,user);
    }
    public CommentResponse delete(User user,Long id, Long cardId){
        Comment findComment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if(!(Objects.equals(findComment.getUser().getId(), user.getId()))){
            throw new IllegalArgumentException("자신의 댓글만 수정 가능합니다.");
        }
        commentRepository.delete(findComment);
        return new CommentResponse(findComment,user);
    }


    @Transactional(readOnly = true)
    public CommentResponse getComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않습니다.")
        );
        return new CommentResponse(comment,comment.getUser());
    }

}
