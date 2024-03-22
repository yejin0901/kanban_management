package com.team8.kanban.domain.comment;

import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.global.common.CommonResponse;
import com.team8.kanban.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @GetMapping("/{cardId}/comments/v1")
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getCommentsV1(
            @PathVariable("cardId") Long cardId
    ){
        List<CommentResponse> comments = commentService.getCommentsV1(cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }
    @GetMapping("/{cardId}/comments/v2")
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getCommentsV2(
            @PathVariable("cardId") Long cardId
    ){
        List<CommentResponse> comments = commentService.getCommentsV2(cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }
    @GetMapping("/{cardId}/comments/v3")
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getCommentsV3(
            @PathVariable("cardId") Long cardId
    ){
        List<CommentResponse> comments = commentService.getCommentsV3(cardId);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<List<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }
    @GetMapping("{cardId}/comments/v4")
    public ResponseEntity<CommonResponse<Page<CommentResponse>>> getCommentsV4(
            @PathVariable("cardId") Long cardId,
            @PageableDefault(size = 5, sort = "createdAt")Pageable pageable
            ){
        Page<CommentResponse> comments = commentService.getCommentsV4(cardId,pageable);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Page<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }
    @GetMapping("{cardId}/comments/v5")
    public ResponseEntity<CommonResponse<Page<CommentResponse>>> getCommentsV5(
            @PathVariable("cardId") Long cardId,
            @PageableDefault(size = 5, sort = "createdAt")Pageable pageable
    ){
        Page<CommentResponse> comments = commentService.getCommentsV5(cardId,pageable);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Page<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }
    @GetMapping("{cardId}/comments/v6")
    public ResponseEntity<CommonResponse<Slice<CommentResponse>>> getCommentsV6(
            @PathVariable("cardId") Long cardId,
            @PageableDefault(size = 5, sort = "createdAt",direction = Sort.Direction.DESC)Pageable pageable
    ){
        Slice<CommentResponse> comments = commentService.getCommentsV6(cardId,pageable);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<Slice<CommentResponse>>builder()
                        .msg("show comments complete!")
                        .data(comments)
                        .build());
    }

}
