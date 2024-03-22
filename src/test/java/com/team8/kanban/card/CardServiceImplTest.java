//package com.team8.kanban.card;
//
//
//import com.team8.kanban.domain.card.dto.CardResponse;
//import com.team8.kanban.domain.card.dto.CreateCardRequest;
//import com.team8.kanban.domain.card.dto.UpdateCardRequest;
//import com.team8.kanban.domain.card.entity.Card;
//import com.team8.kanban.domain.card.entity.CardUser;
//import com.team8.kanban.domain.card.repository.CardRepository;
//import com.team8.kanban.domain.card.repository.CardUserRepository;
//import com.team8.kanban.domain.card.service.CardServiceImpl;
//import com.team8.kanban.domain.user.User;
//import com.team8.kanban.domain.user.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//
//import static com.team8.kanban.global.entity.ColorEnum.RED;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//class CardServiceImplTest {
//    @Mock
//    CardRepository cardRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    CardUserRepository cardUserRepository;
//    @InjectMocks
//    CardServiceImpl cardService;
//
//
//    @Test
//    @DisplayName("section 카드 조회 -성공")
//    void test1() {
//        //given
//        List<CardResponse> responseList = new ArrayList<>();
//        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(2L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        Long sectionId = 1L;
//        responseList.add(response1);
//        responseList.add(response2);
//        given(cardRepository.findCards(any(Long.class))).willReturn(responseList);
//
//        //when
//        List<CardResponse> responses = cardService.getCards(sectionId);
//
//        //then
//        assertEquals(2, responses.size());
//        assertEquals(responseList.get(0).getCardId(), responses.get(0).getCardId());
//    }
//
//    @Test
//    @DisplayName("section 카드 조회 -section에 카드가 존재하지 않는경우")
//    void test2() {
//        //given
//        Long sectionId = 1L;
//
//        given(cardRepository.findCards(any(Long.class))).willThrow(new IllegalArgumentException("해당 section에 Card가 존재하지 않습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.getCards(sectionId));
//
//        //then
//        assertEquals("해당 section에 Card가 존재하지 않습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("단일 카드 조회 -성공")
//    void test3() {
//        //given
//        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        Long cardId = 1L;
//
//        given(cardRepository.findCard(any(Long.class))).willReturn(response1);
//
//        //when
//        CardResponse response = cardService.getCard(cardId);
//
//        //then
//        assertEquals(response1.getCardId(), response.getCardId());
//    }
//
//    @Test
//    @DisplayName("단일 카드 조회 -카드 없음")
//    void test4() {
//        //given
//        Long cardId = 1L;
//
//        given(cardRepository.findCard(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.getCard(cardId));
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 생성 -성공")
//    void test5() {
//        //given
//        CreateCardRequest response1 = CreateCardRequest.builder().cardName("testcard1").description("testdes1")
//                .expiredDate(LocalDateTime.now().plusDays(1)).color("RED").build();
//        User user = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        //when
//        CardResponse response = cardService.createCard(user, response1);
//
//        //then
//        assertEquals(user.getUsername(), response.getUsername());
//        assertEquals(response1.getCardName(), response.getCardName());
//    }
//
//    @Test
//    @DisplayName("카드 수정 -성공")
//    void test6() {
//        //given
//        Long cardId = 1L;
//        Card updateCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
//                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
//        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(updateCard));
//
//
//        //when
//        CardResponse response = cardService.updateCard(loginUser, updateRequest, cardId);
//
//        //then
//        assertEquals(updateRequest.getCardName(), response.getCardName());
//    }
//
//    @Test
//    @DisplayName("카드 수정 -카드가 없을때")
//    void test7() {
//        //given
//        Long cardId = 1L;
//        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
//                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
//        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(cardId)).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.updateCard(loginUser, updateRequest, cardId));
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 수정 -다른유저게시물")
//    void test8() {
//        //given
//        Long cardId = 1L;
//        Card updateCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        UpdateCardRequest updateRequest = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
//                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
//        User anotherUser = User.builder().id(2L).username("hehe").password("123456789").build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(updateCard));
//
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.updateCard(anotherUser, updateRequest, cardId));
//
//        //then
//        assertEquals("권한이 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 삭제 -성공")
//    void test9() {
//        //given
//        Long cardId = 1L;
//        Card deleteCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(deleteCard));
//
//        //when
//        Boolean isDelete = cardService.deleteCard(loginUser, cardId);
//
//        //then
//        assertEquals(true, isDelete);
//    }
//
//    @Test
//    @DisplayName("카드 삭제 -카드가 없을때")
//    void test10() {
//        //given
//        Long cardId = 1L;
//        User loginUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(cardId)).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteCard(loginUser, cardId));
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 삭제 -다른유저 게시물")
//    void test11() {
//        //given
//        Long cardId = 1L;
//        Card deleteCard = Card.builder().cardId(cardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        User anotherUser = User.builder().id(2L).username("hehe").password("123456789").build();
//
//        given(cardRepository.findById(cardId)).willReturn(Optional.of(deleteCard));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteCard(anotherUser, cardId));
//
//        //then
//        assertEquals("권한이 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 포지션 변경 -성공")
//    void test12() {
//        //given
//        Long sectionId = 1L;
//        Long[] cardIdSet = {1L, 3L, 2L};
//
//        List<Card> cardList = new ArrayList<>();
//        Card card1 = Card.builder().cardId(1L).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        Card card2 = Card.builder().cardId(2L).cardName("testcard2").description("testdes2").userid(1L).username("dosal")
//                .position(2L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        Card card3 = Card.builder().cardId(3L).cardName("testcard3").description("testdes3").userid(1L).username("dosal")
//                .position(null).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        cardList.add(card1);
//        cardList.add(card2);
//        cardList.add(card3);
//        List<CardResponse> responseList = new ArrayList<>();
//        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(3L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response3 = CardResponse.builder().cardId(3L).cardName("testcard3").description("testdes2").username("dosal").sectionId(1L).position(2L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        responseList.add(response1);
//        responseList.add(response2);
//        responseList.add(response3);
//        responseList.sort(Comparator.comparing(CardResponse::getPosition));
//
//        given(cardRepository.findCardsBySectionId(sectionId)).willReturn(cardList);
//        given(cardRepository.findCards(sectionId)).willReturn(responseList);
//
//        //when
//        List<CardResponse> responses = cardService.changePosition(sectionId, cardIdSet);
//
//        //then
//        assertEquals(responseList.get(0).getPosition(), responses.get(0).getPosition());
//        assertEquals(responseList.get(1).getPosition(), responses.get(1).getPosition());
//        assertEquals(responseList.get(2).getPosition(), responses.get(2).getPosition());
//        assertEquals(1, responses.get(0).getCardId());
//        assertEquals(2, responses.get(2).getCardId());
//        assertEquals(3, responses.get(1).getCardId());
//    }
//
//    @Test
//    @DisplayName("카드 포지션 변경 -section에 카드가 존재하지 않는 경우")
//    void test13() {
//        //given
//        Long sectionId = 1L;
//        Long[] cardIdSet = {1L, 3L, 2L};
//
//        given(cardRepository.findCardsBySectionId(sectionId)).willThrow(new IllegalArgumentException("해당 section에 Card가 존재하지 않습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.changePosition(sectionId, cardIdSet));
//
//        //then
//        assertEquals("해당 section에 Card가 존재하지 않습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 section 변경 -성공")
//    void test14() {
//        //given
//        Long newSectionId = 1L;
//        Long[] newSectionIdSet = {1L, 3L, 2L};
//        Long cardPosition = 2L;
//        Long changeCardId = 4L;
//
//        Card changeCard = Card.builder().cardId(changeCardId).cardName("changeCardName").description("changeCardDes").userid(1L).username("dosal")
//                .position(30L).sectionId(2L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        List<Card> cardList = new ArrayList<>();
//        Card card1 = Card.builder().cardId(1L).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        Card card2 = Card.builder().cardId(2L).cardName("testcard2").description("testdes2").userid(1L).username("dosal")
//                .position(2L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        Card card3 = Card.builder().cardId(3L).cardName("testcard3").description("testdes3").userid(1L).username("dosal")
//                .position(null).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        cardList.add(card1);
//        cardList.add(card2);
//        cardList.add(card3);
//        cardList.sort(Comparator.comparing(Card::getCardId));
//
//        List<CardResponse> responseList = new ArrayList<>();
//        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(4L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response3 = CardResponse.builder().cardId(3L).cardName("testcard3").description("testdes2").username("dosal").sectionId(1L).position(2L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        CardResponse response4 = CardResponse.builder().cardId(changeCardId).cardName("changeCardName").description("changeCardDes").username("dosal").sectionId(1L).position(3L)
//                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
//        responseList.add(response1);
//        responseList.add(response2);
//        responseList.add(response3);
//        responseList.add(response4);
//        responseList.sort(Comparator.comparing(CardResponse::getPosition));
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(changeCard));
//        given(cardRepository.findCardsBySectionId(any(Long.class))).willReturn(cardList);
//        given(cardRepository.findCards(any(Long.class))).willReturn(responseList);
//
//        //when
//        List<CardResponse> responses = cardService.changeSection(changeCardId, newSectionId, newSectionIdSet, cardPosition);
//
//        //then
//        assertEquals(3L, responses.get(2).getPosition());
//        assertEquals(changeCard.getCardId(), responses.get(2).getCardId());
//        assertEquals(changeCard.getCardName(), responses.get(2).getCardName());
//    }
//
//    @Test
//    @DisplayName("카드 section 변경 -section을 변경할 카드가 없을 때")
//    void test15() {
//        //given
//        Long newSectionId = 1L;
//        Long[] newSectionIdSet = {1L, 3L, 2L};
//        Long cardPosition = 2L;
//        Long changeCardId = 4L;
//
//        given(cardRepository.findById(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.changeSection(changeCardId, newSectionId, newSectionIdSet, cardPosition));
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("카드 section 변경 -변경할 section에 카드가 존재하지 않을때")
//    void test16() {
//        //given
//        Long newSectionId = 1L;
//        Long[] newSectionIdSet = {1L, 3L, 2L};
//        Long cardPosition = 2L;
//        Long changeCardId = 4L;
//
//        Card changeCard = Card.builder().cardId(changeCardId).cardName("changeCardName").description("changeCardDes").userid(1L).username("dosal")
//                .position(30L).sectionId(2L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(changeCard));
//        given(cardRepository.findCardsBySectionId(any(Long.class))).willThrow(new IllegalArgumentException("해당 section에 Card가 존재하지 않습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.changeSection(changeCardId, newSectionId, newSectionIdSet, cardPosition));
//
//        //then
//        assertEquals("해당 section에 Card가 존재하지 않습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 추가 -성공")
//    void test17() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(anotherUser));
//        given(cardUserRepository.findByUserInCard(any(Long.class), any(Long.class))).willReturn(null);
//
//        //when
//        Boolean aBoolean = cardService.addUserByCard(cardOwnerUser, anotherUserId, shareCardId);
//
//        //then
//        assertEquals(true, aBoolean);
//    }
//
//    @Test
//    @DisplayName("공동작업자 추가 -게시물 주인이 아닌사람이 접근했을때")
//    void test18() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.addUserByCard(anotherUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("권한이 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 추가 -해당하는 카드 없을때")
//    void test19() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.addUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 추가 -해당하는 유저 없을때")
//    void test20() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 유저가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.addUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("해당하는 유저가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 추가 -이미 공동작업자로 등록되어있을때")
//    void test21() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        CardUser cardUser = CardUser.builder().card(shareCard).user(anotherUser).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(anotherUser));
//        given(cardUserRepository.findByUserInCard(any(Long.class), any(Long.class))).willReturn(cardUser);
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.addUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("이미 공동작성자인 유저 입니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 삭제 -성공")
//    void test22() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//        CardUser cardUser = CardUser.builder().card(shareCard).user(anotherUser).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(anotherUser));
//        given(cardUserRepository.findByUserInCard(any(Long.class), any(Long.class))).willReturn(cardUser);
//
//        //when
//        Boolean aBoolean = cardService.deleteUserByCard(cardOwnerUser, anotherUserId, shareCardId);
//
//        //then
//        assertEquals(true, aBoolean);
//    }
//
//    @Test
//    @DisplayName("공동작업자 삭제 -게시물 주인이 아닌사람이 접근했을때")
//    void test23() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteUserByCard(anotherUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("권한이 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 삭제 -해당하는 카드 없을때")
//    void test24() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//
//        given(cardRepository.findById(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 삭제 -해당하는 유저 없을때")
//    void test25() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 유저가 없습니다."));
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("해당하는 유저가 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("공동작업자 삭제 - 공동작업자로 등록이 안되어있을때")
//    void test26() {
//        //given
//        Long anotherUserId = 2L;
//        Long shareCardId = 1L;
//        User cardOwnerUser = User.builder().id(1L).username("dosal").password("123456789").build();
//        User anotherUser = User.builder().id(anotherUserId).username("hehe").password("123456789").build();
//        Card shareCard = Card.builder().cardId(shareCardId).cardName("testcard1").description("testdes1").userid(1L).username("dosal")
//                .position(1L).sectionId(1L).expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).build();
//
//        given(cardRepository.findById(any(Long.class))).willReturn(Optional.of(shareCard));
//        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(anotherUser));
//        given(cardUserRepository.findByUserInCard(any(Long.class), any(Long.class))).willReturn(null);
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardService.deleteUserByCard(cardOwnerUser, anotherUserId, shareCardId));
//
//
//        //then
//        assertEquals("공동작성자로 등록되지 않은 유저입니다.", exception.getMessage());
//    }
//}