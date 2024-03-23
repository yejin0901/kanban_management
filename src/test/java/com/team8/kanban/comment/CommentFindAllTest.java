package com.team8.kanban.comment;

import com.team8.kanban.domain.comment.CommentService;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentFindAllTest {
    @Autowired
    CommentService commentService;
    @Test
    @DisplayName("댓글 전체 조회 - jpa interface의 findAll()")
    void getCommentsAllV1(){
        //given
        List<CommentResponse> commentsAllV1 = commentService.getCommentsAllV1();
        //when
        System.out.println("commentsAllV1.size() = " + commentsAllV1.size());
        //then
    }
    @Test
    @DisplayName("댓글 전체 조회 - fetch join")
    void getCommentsAllV2(){
        //given
        List<CommentResponse> commentsAllV1 = commentService.getCommentsAllV2();
        //when
        System.out.println("commentsAllV1.size() = " + commentsAllV1.size());
        //then

    }
    @Test
    @DisplayName("댓글 전체 조회 - projection으로 dto로 조회")
    void getCommentsAllV3(){
        //given
        List<CommentResponse> commentsAllV1 = commentService.getCommentsAllV3();
        //when
        System.out.println("commentsAllV1.size() = " + commentsAllV1.size());
        //then
    }
}
