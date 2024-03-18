package com.team8.kanban.domain.user;

import com.team8.kanban.domain.user.dto.DeleteRequest;
import com.team8.kanban.domain.user.dto.PasswordRequest;
import com.team8.kanban.domain.user.dto.UserRequest;
import com.team8.kanban.domain.user.dto.UserResponse;
import com.team8.kanban.global.exception.CommonResponse;
import com.team8.kanban.global.jwt.JwtUtil;
import com.team8.kanban.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth/signup")
    public ResponseEntity<CommonResponse<UserResponse>> signup(@RequestBody UserRequest userRequest) {
        UserResponse signupedUser = userService.signup(userRequest);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<UserResponse>builder()
                        .msg("signup complete!")
                        .data(signupedUser)
                        .build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<CommonResponse<UserResponse>> login(@RequestBody UserRequest userRequest, HttpServletResponse response) {
        UserResponse loginedUser = userService.login(userRequest);
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequest.getUsername()));
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<UserResponse>builder()
                        .msg("login complete!")
                        .data(loginedUser)
                        .build());
    }

    @PostMapping("/users")
    public ResponseEntity<CommonResponse<UserResponse>> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequest passwordRequest) {
        UserResponse updateUserResponse = userService.updatePassword(userDetails.getUser(), passwordRequest);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<UserResponse>builder()
                        .msg("update password complete!")
                        .data(updateUserResponse)
                        .build());
    }

    @DeleteMapping("/users")
    public ResponseEntity<CommonResponse<UserResponse>> delete(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            , @RequestBody DeleteRequest deleteRequest) {
        UserResponse deletedUser = userService.delete(userDetails.getUser(), deleteRequest);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(CommonResponse.<UserResponse>builder()
                        .msg("delete complete!")
                        .data(deletedUser)
                        .build());
    }
}
