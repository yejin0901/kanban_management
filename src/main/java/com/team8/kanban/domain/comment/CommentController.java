package com.team8.kanban.domain.comment;

import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.global.common.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}/comments")
    public ResponseEntity<CommonResponse<CommentResponse>> create(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody CommentRequest commentRequest
            ,@PathVariable("cardId") Long cardId){
        CommentResponse createdCommentResponse = commentService.create(userDetails.getUser(), commentRequest, cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CommentResponse>builder()
                        .msg("create comment complete!")
                        .data(createdCommentResponse)
                        .build());
    }
    @PatchMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponse>> update(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody CommentRequest commentRequest
            ,@PathVariable("cardId") Long cardId
            ,@PathVariable("commentId") Long commentId){
        CommentResponse updateCommentResponse = commentService.update(userDetails.getUser(),commentId,commentRequest, cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CommentResponse>builder()
                        .msg("update comment complete!")
                        .data(updateCommentResponse)
                        .build());
    }
    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponse>> delete(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@PathVariable("cardId") Long cardId
            ,@PathVariable("commentId") Long commentId
    ){
        CommentResponse deletedCommentResponse = commentService.delete(userDetails.getUser(), commentId, cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<CommentResponse>builder()
                        .msg("delete comment complete!")
                        .data(deletedCommentResponse)
                        .build());
    }

}
