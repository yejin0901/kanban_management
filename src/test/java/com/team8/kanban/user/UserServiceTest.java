package com.team8.kanban.user;

import com.team8.kanban.domain.user.UserService;
import com.team8.kanban.domain.user.dto.UserRequest;
import com.team8.kanban.domain.user.dto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void signup(){
        //given
        UserRequest userRequest = new UserRequest("testUser","1234");
        //when
        UserResponse signuped = userService.signup(userRequest);
        //then
        assertThat(userRequest.getUsername()).isEqualTo(signuped.getUsername());

    }
    @Test
    @DisplayName("중복된 회원 가입")
    void duplicatedSignUp(){
        //given
        UserRequest userRequest1 = new UserRequest("testUser","1234");
        UserRequest userRequest2 = new UserRequest("testUser","12345");
        //when
        userService.signup(userRequest1);
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.signup(userRequest2));
    }
    @Test
    @DisplayName("로그인")
    void login(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        userService.signup(signupRequest);
        UserRequest loginRequest = new UserRequest("testUser", "1234");
        //when
        UserResponse logined = userService.login(loginRequest);
        //then
        assertThat(logined.getUsername()).isEqualTo(loginRequest.getUsername());
    }
    @Test
    @DisplayName("없는 아이디로 로그인")
    void loginByNonExistentUser(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        userService.signup(signupRequest);
        //when
        UserRequest loginRequest = new UserRequest("otherTestUser", "1234");
        //then
        assertThrows(NullPointerException.class, () -> userService.login(loginRequest));
    }
    @Test
    @DisplayName("비밀번호가 틀렸을 때")
    void wrongPassword(){
        UserRequest signupRequest = new UserRequest("testUser","1234");
        userService.signup(signupRequest);
        //when
        UserRequest loginRequest = new UserRequest("testUser", "7890");
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.login(loginRequest));
    }
}
