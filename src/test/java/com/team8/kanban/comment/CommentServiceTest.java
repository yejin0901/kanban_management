package com.team8.kanban.comment;

import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.card.repository.CardRepository;
import com.team8.kanban.domain.card.service.CardService;
import com.team8.kanban.domain.comment.CommentService;
import com.team8.kanban.domain.comment.dto.CommentRequest;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.entity.ColorEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceTest
{
    @Autowired
    CommentService commentService;

    @Autowired
    CardService cardService;
    @PersistenceContext
    EntityManager em;
    User userA;

    Card card;

    @BeforeEach
    void before(){

        userA = new User("userA", "1234");
        //card = new Card(100000L,"cardName","description", userA.getId(), userA.getUsername(), LocalDateTime.now(), ColorEnum.BLACK,1L,1L);
    }

    @Test
    @DisplayName("댓글 생성")
    void create(){
        //given

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

        CommentRequest commentRequest1 = new CommentRequest("commentA-A");
        CommentResponse commentResponse1 = commentService.create(userA, commentRequest1, 1L);
        //when
        commentService.delete(userA, commentResponse1.getId(), 1L);
        //then
        assertThrows(NullPointerException.class,() -> commentService.getComment(commentResponse1.getId()));
    }

    @Test
    @DisplayName("댓글 조회 - 1+n문제 발생")
    void getCommentsV1(){

        CreateCardRequest createCardRequest = CreateCardRequest.builder()
                .cardName("cardNAme")
                .description("description")
                .expiredDate(LocalDateTime.now())
                .color("RED")
                .sectionId(1L)
                .build();

        CardResponse card = cardService.createCard(userA, createCardRequest);

        for (int i = 0 ; i< 100; i++){
            commentService.create(userA, new CommentRequest("comment" + i), card.getCardId());
        }

        List<CommentResponse> commentsV1 = commentService.getCommentsV1(card.getCardId());
        assertEquals(commentsV1.size(), 100);
    }
    @Test
    @DisplayName("댓글 조회 - Dto로 조회")
    void getCommentsV2(){

        CreateCardRequest createCardRequest = CreateCardRequest.builder()
                .cardName("cardNAme")
                .description("description")
                .expiredDate(LocalDateTime.now())
                .color("RED")
                .sectionId(1L)
                .build();

        CardResponse card = cardService.createCard(userA, createCardRequest);

        for (int i = 0 ; i< 100; i++){
            commentService.create(userA, new CommentRequest("comment" + i), card.getCardId());
        }

        List<CommentResponse> commentsV2 = commentService.getCommentsV2(card.getCardId());
        assertEquals(commentsV2.size(), 100);
    }
    @Test
    @DisplayName("댓글 조회 - fetch조인")
    void getCommentsV3(){

        CreateCardRequest createCardRequest = CreateCardRequest.builder()
                .cardName("cardNAme")
                .description("description")
                .expiredDate(LocalDateTime.now())
                .color("RED")
                .sectionId(1L)
                .build();

        CardResponse card = cardService.createCard(userA, createCardRequest);

        for (int i = 0 ; i< 100; i++){
            commentService.create(userA, new CommentRequest("comment" + i), card.getCardId());
        }

        List<CommentResponse> commentsV3 = commentService.getCommentsV3(card.getCardId());
        assertEquals(commentsV3.size(), 100);
    }
}
