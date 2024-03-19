package com.team8.kanban.domain.user;

import com.team8.kanban.domain.user.dto.DeleteRequest;
import com.team8.kanban.domain.user.dto.PasswordRequest;
import com.team8.kanban.domain.user.dto.UserRequest;
import com.team8.kanban.domain.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    //회원가입
    public UserResponse signup(UserRequest userRequest){
        if(userRepository.findByUsername(userRequest.getUsername()).isPresent()){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        User user = new User(userRequest.getUsername(), encodedPassword);

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    //로그인
    public UserResponse login(UserRequest userRequest) {
        // 현재 세션에서 이미 로그인된 사용자 확인
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            throw new DuplicateKeyException("이미 로그인된 사용자입니다.");
        }

        User findUser = userRepository.findByUsername(userRequest.getUsername()).orElseThrow(
                () -> new NullPointerException("없는 회원입니다.")
        );

        if (!passwordEncoder.matches(userRequest.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return new UserResponse(findUser);
    }

    public UserResponse delete(User user, DeleteRequest deleteRequest) {
        if (!passwordEncoder.matches(deleteRequest.getPassword(),user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        userRepository.delete(user);
        return new UserResponse(user);
    }

    public UserResponse updatePassword(User user, PasswordRequest passwordRequest) {
        if (!Objects.equals(passwordRequest.getCheckPassword(), passwordRequest.getNewPassword())) {
            throw new IllegalArgumentException("비밀번호와 확인 비밃번호가 다릅니다.");
        }

        User findUser = userRepository.findById(user.getId()).orElseThrow();

        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("작성한 비밀번호와 현재 비밀번호가 다릅니다.");
        }
        if(passwordEncoder.matches(passwordRequest.getNewPassword(), findUser.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호와 새로운 비밀번호가 같습니다.");
        }
        String encodedNewPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
        findUser.updatePassword(encodedNewPassword);
        return new UserResponse(user);
    }
}
