package com.team8.kanban.card;

import com.team8.kanban.domain.card.dto.CardCommentResponse;
import com.team8.kanban.domain.card.dto.SectionIdCardRequest;
import com.team8.kanban.domain.card.service.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

@SpringBootTest
public class CardFindAllTest {

    @Autowired
    private CardService cardService;

    @Test
    @DisplayName("카드조회 - Slice X(기준데이터: 30,000건")
    void getCard() {
        //given
        SectionIdCardRequest request = SectionIdCardRequest.builder().sectionId(2L).build();
        long startTime = System.currentTimeMillis();
        //when - then
        try {
            List<CardCommentResponse> cards = cardService.getCardsV1(request.getSectionId());
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("조회에 걸린시간: " + executionTime);
        }
    }

    @Test
    @DisplayName("카드조회 - Slice O(기준데이터: 30,000건")
    void getCardV2() {
        //given
        SectionIdCardRequest request = SectionIdCardRequest.builder().sectionId(2L).build();
        PageRequest pageable = PageRequest.of(0, 10);
        long startTime = System.currentTimeMillis();

        //when - then
        try {
            Slice<CardCommentResponse> cards = cardService.getCards(request.getSectionId(), pageable);
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("조회에 걸린시간: " + executionTime);
        }
    }
}
