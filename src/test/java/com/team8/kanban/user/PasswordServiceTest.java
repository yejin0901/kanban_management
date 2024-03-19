package com.team8.kanban.user;

import com.team8.kanban.domain.user.User;
import com.team8.kanban.domain.user.UserRepository;
import com.team8.kanban.domain.user.UserService;
import com.team8.kanban.domain.user.dto.PasswordRequest;
import com.team8.kanban.domain.user.dto.UserRequest;
import com.team8.kanban.domain.user.dto.UserResponse;
import com.team8.kanban.global.config.WebSecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PasswordServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WebSecurityConfig webSecurityConfig;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 변경")
    void passwordChange(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        UserResponse signuped = userService.signup(signupRequest);

        User loginedUser = userRepository.findByUsername(signuped.getUsername()).orElseThrow();

        PasswordRequest passwordRequest = new PasswordRequest("1234", "12345", "12345");

        //when
        UserResponse userResponse = userService.updatePassword(loginedUser, passwordRequest);
        User findUser = userRepository.findByUsername(userResponse.getUsername()).orElseThrow();
        //then

        assertThat(passwordEncoder.matches("12345", findUser.getPassword())).isTrue();
    }
    @Test
    @DisplayName("새로운 비밀번호와 확인 비밀번호가 다를 때")
    void differentNewPasswordAndCheckPassword(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        UserResponse signuped = userService.signup(signupRequest);

        User loginedUser = userRepository.findByUsername(signuped.getUsername()).orElseThrow();

        PasswordRequest passwordRequest = new PasswordRequest("1234", "12345", "56789");
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(loginedUser, passwordRequest));

    }
    @Test
    @DisplayName("현재 비밀번호와 변경전 비밀번호가 다를 때")
    void differentCurrentPasswordAndOldPassword(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        UserResponse signuped = userService.signup(signupRequest);

        User loginedUser = userRepository.findByUsername(signuped.getUsername()).orElseThrow();

        PasswordRequest passwordRequest = new PasswordRequest("9999", "12345", "12345");
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(loginedUser, passwordRequest));
    }
    @Test
    @DisplayName("현재 비밀번호와 새로운 비밀번호가 같을 떄")
    void SameNewPasswordAndOldPassword(){
        //given
        UserRequest signupRequest = new UserRequest("testUser","1234");
        UserResponse signuped = userService.signup(signupRequest);

        User loginedUser = userRepository.findByUsername(signuped.getUsername()).orElseThrow();

        PasswordRequest passwordRequest = new PasswordRequest("1234", "1234", "1234");
        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(loginedUser, passwordRequest));
    }


}
