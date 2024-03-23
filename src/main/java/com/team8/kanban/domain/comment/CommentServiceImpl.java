package com.team8.kanban.domain.comment;


import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.card.repository.CardRepository;
import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.comment.repository.CommentRepository;
import com.team8.kanban.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public CommentResponse create(User user, CommentRequest commentRequest , Long cardId) {
        Card findCard = cardRepository.findById(cardId).orElseThrow();

        Comment createComment = new Comment(commentRequest.getContent(), user, findCard);
        Comment savedComment = commentRepository.save(createComment);
        return new CommentResponse(savedComment);
    }

    public CommentResponse update(User user,Long id,CommentRequest updateCommentRequest, Long cardId) {

        Comment findComment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if(!(Objects.equals(findComment.getUser().getId(), user.getId()))){
            throw new IllegalArgumentException("자신의 댓글만 수정 가능합니다.");
        }
        findComment.updateContent(updateCommentRequest.getContent());
        return new CommentResponse(findComment);
    }
    public CommentResponse delete(User user,Long id, Long cardId){
        Comment findComment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if(!(Objects.equals(findComment.getUser().getId(), user.getId()))){
            throw new IllegalArgumentException("자신의 댓글만 수정 가능합니다.");
        }
        commentRepository.delete(findComment);
        return new CommentResponse(findComment);
    }


    @Transactional(readOnly = true)
    public CommentResponse getComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않습니다.")
        );
        return new CommentResponse(comment);
    }

    public List<CommentResponse> getCommentsV1(Long cardId){
        List<Comment> comments = commentRepository.findAllByCardIdV1(cardId);
        return comments.stream().map(CommentResponse::new).toList();
    }
    public List<CommentResponse> getCommentsV2(Long cardId){
        return commentRepository.V2findAllByCardId(cardId);
    }

    public List<CommentResponse> getCommentsV3(Long cardId){
        List<Comment> comments = commentRepository.findAllByCardIdV3(cardId);
        return comments.stream().map(CommentResponse::new).toList();
    }
    public Page<CommentResponse> getCommentsV4(Long cardId, Pageable pageable){
        Page<Comment> comments = commentRepository.findAllByCardIdV4(cardId, pageable);
        return comments.map(CommentResponse::new);
    }
    public Page<CommentResponse> getCommentsV5(Long cardId, Pageable pageable){
        Page<Comment> comments = commentRepository.findAllByCardIdV5(cardId, pageable);
        return comments.map(CommentResponse::new);
    }
    public Slice<CommentResponse> getCommentsV6(Long cardId, Pageable pageable){
        Slice<Comment> comments = commentRepository.findAllByCardIdV6(cardId, pageable);
        return comments.map(CommentResponse::new);
    }
    public List<CommentResponse> getComment(Long cardId,CommentRequest commentRequest){
        List<Comment> findComments = commentRepository.findByContent(cardId,commentRequest.getContent());
        return findComments.stream().map(CommentResponse::new).toList();
    }
    public List<CommentResponse> getCommentsAllV1(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentResponse::new).toList();
    }
    public List<CommentResponse> getCommentsAllV2(){
        List<Comment> comments = commentRepository.findAllV2();
        return comments.stream().map(CommentResponse::new).toList();
    }
    public List<CommentResponse> getCommentsAllV3(){
        return commentRepository.findAllV3();
    }
}
