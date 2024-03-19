package com.team8.kanban.card;

import com.team8.kanban.domain.card.Card;
import com.team8.kanban.domain.card.CardRepository;
import com.team8.kanban.domain.card.CardServiceImpl;
import com.team8.kanban.domain.card.dto.CardResponse;
import com.team8.kanban.domain.card.dto.CreateCardRequest;
import com.team8.kanban.domain.card.dto.UpdateCardRequest;
import com.team8.kanban.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.team8.kanban.global.entity.ColorEnum.RED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CardServiceImplTest {
    @Mock
    CardRepository cardRepository;
    @InjectMocks
    CardServiceImpl cardService;


    @Test
    @DisplayName("전체 카드 조회 -성공")
    void test1() {
        //given
        List<CardResponse> responseList = new ArrayList<>();
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response1);
        responseList.add(response2);
        given(cardRepository.findCards()).willReturn(responseList);

        //when
        List<CardResponse> responses = cardService.getCards();

        //then
        assertEquals(2, responses.size());
        assertEquals(responseList.get(0).getCardId(), responses.get(0).getCardId());
    }

    @Test
    @DisplayName("단일 카드 조회 -성공")
    void test2() {
        //given
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        Long cardId = 1L;
        given(cardRepository.findCard(any(Long.class))).willReturn(response1);

        //when
        CardResponse response = cardService.getCard(cardId);

        //then
        assertEquals(response1.getCardId(), response.getCardId());
    }

    @Test
    @DisplayName("단일 카드 조회 -카드 없음")
    void test3() {
        //given
        Long cardId = 1L;
        given(cardRepository.findCard(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> cardService.getCard(cardId));

        //then
        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("카드 생성 -성공")
    void test4() {
        //given
        CreateCardRequest response1 = CreateCardRequest.builder().cardName("testcard1").description("testdes1")
                .expiredDate(LocalDateTime.now().plusDays(1)).color("RED").build();
        User user = User.builder().id(1L).username("dosal").password("123456789").build();

        //when
        CardResponse response = cardService.createCard(user, response1);

        //then
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(response1.getCardName(), response.getCardName());
    }

    @Test
    @DisplayName("카드 수정 -성공")
    void test5() {
        //given
        Long cardId = 1L;
        Card updateCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(updateCard));


        //when
        CardResponse response = cardService.updateCard(loginUser, updateRequest, cardId);

        //then
        assertEquals(updateRequest.getCardName(), response.getCardName());
    }

    @Test
    @DisplayName("카드 수정 -카드가 없을때")
    void test6() {
        //given
        Long cardId = 1L;
        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> cardService.updateCard(loginUser, updateRequest, cardId));

        //then
        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("카드 수정 -다른유저게시물")
    void test7() {
        //given
        Long cardId = 1L;
        Card updateCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        User anotherUser = User.builder().id(2L).username("hehe").password("123456789").build();
        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(updateCard));


        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> cardService.updateCard(anotherUser, updateRequest, cardId));

        //then
        assertEquals("권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("카드 삭제 -성공")
    void test8() {
        //given
        Long cardId = 1L;
        Card deleteCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(deleteCard));

        //when
        Boolean isDelete = cardService.deleteCard(loginUser, cardId);

        //then
        assertEquals(true, isDelete);
    }

    @Test
    @DisplayName("카드 삭제 -카드가 없을때")
    void test9() {
        //given
        Long cardId = 1L;
        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> cardService.deleteCard(loginUser, cardId));

        //then
        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("카드 삭제 -다른유저 게시물")
    void test10() {
        //given
        Long cardId = 1L;
        Card deleteCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
        User anotherUser = User.builder().id(2L).username("hehe").password("123456789").build();
        given(cardRepository.findById(cardId)).willReturn(Optional.of(deleteCard));

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> cardService.deleteCard(anotherUser, cardId));

        //then
        assertEquals("권한이 없습니다.", exception.getMessage());
    }
}