//package com.team8.kanban.card;
//
//import com.team8.kanban.domain.card.Card;
//import com.team8.kanban.domain.card.CardRepository;
//import com.team8.kanban.domain.card.CardRepositoryImpl;
//import com.team8.kanban.domain.card.dto.CardResponse;
//import com.team8.kanban.domain.user.User;
//import com.team8.kanban.domain.user.UserRepository;
//import com.team8.kanban.global.config.JpaConfig;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//
//import static com.team8.kanban.global.entity.ColorEnum.BLACK;
//import static com.team8.kanban.global.entity.ColorEnum.RED;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Import(JpaConfig.class)
//@ActiveProfiles("test")
//class CardRepositoryTest {
//    @Autowired
//    CardRepository cardRepository;
//    @Autowired
//    CardRepositoryImpl cardRepositoryImpl;
//    @Autowired
//    UserRepository userRepository;
//
//    private Card card1;
//    private Card card2;
//    private User loginUser;
//
//
//    void setup() {
//        loginUser = User.builder().username("dosal").password("123456789").build();
//        userRepository.save(loginUser);
//        card1 = Card.builder().cardName("testcard1").description("testdes1").userid(loginUser.getId()).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).sectionId(1L).position(1L).build();
//        cardRepository.save(card1);
//        card2 = Card.builder().cardName("testcard2").description("testdes2").userid(loginUser.getId()).username("dosal")
//                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(BLACK).sectionId(1L).position(2L).build();
//        cardRepository.save(card2);
//    }
//
//    @Test
//    @DisplayName("전체 카드 조회 -성공")
//    void test1() {
//        //given
//        setup();
//        Long sectionId = 1L;
//        //when
//        List<CardResponse> responses = cardRepositoryImpl.findCards(sectionId);
//
//        //then
//        assertEquals(2, responses.size());
//        assertEquals(1, responses.get(0).getPosition());
//    }
//
//    @Test
//    @DisplayName("전체 카드 조회 -카드가없을때")
//    void test2() {
//        //given
//        Long sectionId = 1L;
//        //when
//        List<CardResponse> responses = cardRepositoryImpl.findCards(sectionId);
//
//        //then
//        assertEquals(0, responses.size());
//    }
//
//    @Test
//    @DisplayName("단일 카드 조회 -성공")
//    void test3() {
//        //given
//        setup();
//        Long cardId = card1.getCardId();
//
//        //when
//        CardResponse response = cardRepositoryImpl.findCard(cardId);
//
//        //then
//        assertFalse(Objects.isNull(response));
//        assertEquals(cardId, response.getCardId());
//    }
//
//    @Test
//    @DisplayName("단일 카드 조회 -카드가 없을때")
//    void test4() {
//        //given
//        Long cardId = 1L;
//
//        //when
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
//                -> cardRepositoryImpl.findCard(cardId));
//
//        //then
//        assertEquals("해당하는 카드가 없습니다.", exception.getMessage());
//    }
//}