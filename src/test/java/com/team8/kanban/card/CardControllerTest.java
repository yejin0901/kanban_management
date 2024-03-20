package com.team8.kanban.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team8.kanban.domain.card.controller.CardController;
import com.team8.kanban.domain.card.dto.*;
import com.team8.kanban.domain.card.service.CardService;
import com.team8.kanban.domain.user.User;
import com.team8.kanban.global.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = CardController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
@ActiveProfiles("test")
class CardControllerTest {
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CardService cardService;

    @BeforeEach
    void mockMVCSetup() {
        User mockUser = mock(User.class); //mockUser 생성
        UserDetailsImpl mockUserDetails = mock(UserDetailsImpl.class);
        given(mockUserDetails.getUser()).willReturn(mockUser);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(mockUserDetails, null));

        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("section 별 카드 조회 -성공")
    void test1() throws Exception {
        //given
        List<CardResponse> responseList = new ArrayList<>();
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response1);
        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(2L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response2);
        given(cardService.getCards(any(Long.class))).willReturn(responseList);
        Long sectionId = 1L;

        String getInfo = objectMapper.writeValueAsString(sectionId);

        //when-then
        mockMvc.perform(get("/cards")
                        .content(getInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("조회 되었습니다."))
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("section 별 카드 조회 -없을때도 예외 없이 처리")
    void test2() throws Exception {
        //given
        List<CardResponse> responseList = new ArrayList<>();
        given(cardService.getCards(any(Long.class))).willReturn(responseList);
        Long sectionId = 1L;

        String getInfo = objectMapper.writeValueAsString(sectionId);

        //when-then
        mockMvc.perform(get("/cards")
                        .content(getInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("조회 되었습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("단일 카드 조회 -성공")
    void test3() throws Exception {
        //given
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();

        given(cardService.getCard(any(Long.class))).willReturn(response1);

        //when-then
        mockMvc.perform(get("/cards/{cardId}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("조회 되었습니다."))
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("단일 카드 조회 -카드가 없을때")
    void test() throws Exception {
        //given
        given(cardService.getCard(any(Long.class))).willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        //when-then
        mockMvc.perform(get("/cards/{cardId}", 1L))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 카드가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 생성 -성공")
    void test6() throws Exception {
        //given
        CreateCardRequest request = CreateCardRequest.builder().cardName("testcard1").description("testdes1")
                .expiredDate(LocalDateTime.now().plusDays(1)).color("RED").build();
        CardResponse response = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        given(cardService.createCard(any(User.class), any(CreateCardRequest.class))).willReturn(response);

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("Card 생성 성공"))
                .andExpect(jsonPath("$.data.cardId").value("1"))
                .andExpect(jsonPath("$.data.cardName").value("testcard1"))
                .andExpect(jsonPath("$.data.description").value("testdes1"))
                .andExpect(jsonPath("$.data.username").value("dosal"))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 수정 -성공")
    void test7() throws Exception {
        //given
        UpdateCardRequest request = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        CardResponse response = CardResponse.builder().cardId(1L).cardName("update-testcard1").description("update-testdes1").username("dosal")
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        given(cardService.updateCard(any(User.class), any(UpdateCardRequest.class), any(Long.class))).willReturn(response);

        String putInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(patch("/cards/{cardId}", 1L)
                        .content(putInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("Card 수정 성공"))
                .andExpect(jsonPath("$.data.cardId").value("1"))
                .andExpect(jsonPath("$.data.cardName").value("update-testcard1"))
                .andExpect(jsonPath("$.data.description").value("update-testdes1"))
                .andExpect(jsonPath("$.data.username").value("dosal"))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 수정 -카드가 없을때")
    void test8() throws Exception {
        //given
        UpdateCardRequest request = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        given(cardService.updateCard(any(User.class), any(UpdateCardRequest.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        String putInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(patch("/cards/{cardId}", 1L)
                        .content(putInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 카드가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 수정 -다른유저게시물")
    void test9() throws Exception {
        //given
        UpdateCardRequest request = UpdateCardRequest.builder().cardName("update-testcard1").description("update-testdes1")
                .expiredDate(LocalDateTime.now().plusDays(2)).color("BLACK").build();
        given(cardService.updateCard(any(User.class), any(UpdateCardRequest.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("권한이 없습니다."));

        String putInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(patch("/cards/{cardId}", 1L)
                        .content(putInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("권한이 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 삭제 -성공")
    void test10() throws Exception {
        //given
        given(cardService.deleteCard(any(User.class), any(Long.class))).willReturn(true);

        //when-then
        mockMvc.perform(delete("/cards/{cardId}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("Card 삭제 성공"))
                .andExpect(jsonPath("$.data").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 삭제 -카드가 없을때")
    void test11() throws Exception {
        //given
        given(cardService.deleteCard(any(User.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        //when-then
        mockMvc.perform(delete("/cards/{cardId}", 1L))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 카드가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 삭제 -다른유저게시물")
    void test12() throws Exception {
        //given
        given(cardService.deleteCard(any(User.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("권한이 없습니다."));


        //when-then
        mockMvc.perform(delete("/cards/{cardId}", 1L))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("권한이 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 position 변경 -성공")
    void test13() throws Exception {
        //given
        List<CardResponse> responseList = new ArrayList<>();
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response1);
        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(2L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response2);
        responseList.sort(Comparator.comparing(CardResponse::getPosition));
        PositionChangeRequest request = PositionChangeRequest.builder().sectionId(1L).positionSet(new Long[]{1L, 2L}).build();

        given(cardService.changePosition(any(Long.class), any(Long[].class))).willReturn(responseList);
        String postInfo = objectMapper.writeValueAsString(request);


        //when-then
        mockMvc.perform(post("/cards/position")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("position이 변경되었습니다."))
                .andExpect(jsonPath("$.data[0].position").value(1))
                .andExpect(jsonPath("$.data[1].position").value(2))
                .andDo(print());
    }


    @Test
    @DisplayName("카드 section 변경 -성공")
    void test14() throws Exception {
        //given
        List<CardResponse> responseList = new ArrayList<>();
        CardResponse response1 = CardResponse.builder().cardId(1L).cardName("testcard1").description("testdes1").username("dosal").sectionId(1L).position(1L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response1);
        CardResponse response2 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(2L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response2);
        CardResponse response3 = CardResponse.builder().cardId(2L).cardName("testcard2").description("testdes2").username("dosal").sectionId(1L).position(3L)
                .expiredDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now()).modifiedAt(LocalDateTime.now()).build();
        responseList.add(response3);
        responseList.sort(Comparator.comparing(CardResponse::getPosition));
        SectionChangeRequest request = SectionChangeRequest.builder().cardId(1L).newSectionId(1L).newSectionIdSet(new Long[]{1L, 2L}).cardPositionId(2L).build();

        given(cardService.changeSection(any(Long.class), any(Long.class), any(Long[].class), any(Long.class))).willReturn(responseList);
        String postInfo = objectMapper.writeValueAsString(request);


        //when-then
        mockMvc.perform(post("/cards/section")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("section이 변경되었습니다."))
                .andExpect(jsonPath("$.data[0].position").value(1))
                .andExpect(jsonPath("$.data[1].position").value(2))
                .andExpect(jsonPath("$.data[2].position").value(3))
                .andExpect(jsonPath("$.data[0].sectionId").value(1))
                .andExpect(jsonPath("$.data[1].sectionId").value(1))
                .andExpect(jsonPath("$.data[2].sectionId").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 추가 -성공")
    void test15() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.addUserByCard(any(User.class), any(Long.class), any(Long.class))).willReturn(true);

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards/{cardId}/user", 1L)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("추가 되었습니다."))
                .andExpect(jsonPath("$.data").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 추가 -본인의 카드가 아닐때")
    void test16() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.addUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("권한이 없습니다."));

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards/{cardId}/user", 1L)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("권한이 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 추가 -카드가 없을때")
    void test17() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.addUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards/{cardId}/user", 1L)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 카드가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 추가 -추가할 유저가 없을때")
    void test18() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.addUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 유저가 없습니다."));

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards/{cardId}/user", 1L)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 유저가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 추가 -이미 추가됬을때")
    void test19() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.addUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("이미 공동작성자인 유저 입니다."));

        String postInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(post("/cards/{cardId}/user", 1L)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("이미 공동작성자인 유저 입니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }


    @Test
    @DisplayName("카드 공동작업자 삭제 -성공")
    void test20() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.deleteUserByCard(any(User.class), any(Long.class), any(Long.class))).willReturn(true);

        String deleteInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(delete("/cards/{cardId}/user", 1L)
                        .content(deleteInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.msg").value("삭제 되었습니다."))
                .andExpect(jsonPath("$.data").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 삭제 -카드가 없을때")
    void test21() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.deleteUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 카드가 없습니다."));

        String deleteInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(delete("/cards/{cardId}/user", 1L)
                        .content(deleteInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 카드가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 삭제 -카드가 없을때")
    void test22() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.deleteUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("해당하는 유저가 없습니다."));

        String deleteInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(delete("/cards/{cardId}/user", 1L)
                        .content(deleteInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("해당하는 유저가 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("카드 공동작업자 삭제 -공동작업자로 등록이 안되어있을때")
    void test23() throws Exception {
        //given
        UserInCardRequest request = UserInCardRequest.builder().userId(2L).build();
        given(cardService.deleteUserByCard(any(User.class), any(Long.class), any(Long.class)))
                .willThrow(new IllegalArgumentException("공동작성자로 등록되지 않은 유저입니다."));

        String deleteInfo = objectMapper.writeValueAsString(request);

        //when-then
        mockMvc.perform(delete("/cards/{cardId}/user", 1L)
                        .content(deleteInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.msg").value("공동작성자로 등록되지 않은 유저입니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }
}