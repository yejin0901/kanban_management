package com.team8.kanban.comment;

import com.team8.kanban.domain.comment.CommentService;
import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceTest
{
    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성")
    void create(){
        //given
        User userA = new User("userA", "1234");
        CommentRequest commentRequest = new CommentRequest("commentA-A");

        //when
        CommentResponse commentResponse = commentService.create(userA, commentRequest, 1L);

        //then
        assertEquals(commentRequest.getContent(),commentResponse.getContent());
    }
    @Test
    @DisplayName("댓글 수정")
    void update(){
        //given
        User userA = new User("userA", "1234");
        CommentRequest commentRequest = new CommentRequest("commentA-A");
        CommentResponse commentResponse = commentService.create(userA, commentRequest, 1L);

        //when
        CommentRequest updateRequest = new CommentRequest("commentA-A update");
        CommentResponse updateCommentResponse = commentService.update(userA,commentResponse.getId(), updateRequest, 1L);

        //then
        assertEquals(updateCommentResponse.getContent(),updateRequest.getContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    void delete(){
        //given
        User userA = new User("userA", "1234");
        CommentRequest commentRequest1 = new CommentRequest("commentA-A");
        CommentResponse commentResponse1 = commentService.create(userA, commentRequest1, 1L);
        //when
        commentService.delete(userA, commentResponse1.getId(), 1L);
        //then
        assertThrows(NullPointerException.class,() -> commentService.getComment(commentResponse1.getId()));
    }

}
