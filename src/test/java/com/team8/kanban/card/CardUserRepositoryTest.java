package com.team8.kanban.card;

import com.team8.kanban.domain.card.entity.Card;
import com.team8.kanban.domain.card.entity.CardUser;
import com.team8.kanban.domain.card.repository.CardRepository;
import com.team8.kanban.domain.card.repository.CardUserRepository;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.domain.user.UserRepository;
import com.team8.kanban.global.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.team8.kanban.global.entity.ColorEnum.RED;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
class CardUserRepositoryTest {
    @Autowired
    CardUserRepository cardUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;

    private User user;
    private Card card;

    void setup() {
        user = User.builder().username("dosal").password("123456789").build();
        userRepository.save(user);
        card = Card.builder().cardName("testcard1").description("testdes1").userid(user.getId()).username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).colorEnum(RED).sectionId(1L).position(1L).build();
        cardRepository.save(card);
    }

    @Test
    @DisplayName("user와 card를 가지고 조회 -성공")
    void test1() {
        //given
        setup();
        CardUser cardUser = CardUser.builder().user(user).card(card).build();
        cardUserRepository.save(cardUser);

        //when
        CardUser searchCardUser = cardUserRepository.findByUserInCard(user.getId(), card.getCardId());

        //then

        assertNotNull(searchCardUser);
        assertEquals(user.getId(), searchCardUser.getUser().getId());
        assertEquals(card.getCardId(), searchCardUser.getCard().getCardId());
    }

    @Test
    @DisplayName("user와 card를 가지고 조회 -존재하지 않을 때")
    void test2() {
        //given
        Long notUserId = 1000L;
        Long notCardId = 1000L;
        //when
        CardUser searchCardUser = cardUserRepository.findByUserInCard(notUserId, notCardId);
        //then

        assertNull(searchCardUser);
    }
}