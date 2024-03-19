package com.team8.kanban.domain.user;

import com.team8.kanban.domain.user.dto.DeleteRequest;
import com.team8.kanban.domain.user.dto.PasswordRequest;
import com.team8.kanban.domain.user.dto.UserRequest;
import com.team8.kanban.domain.user.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse signup(UserRequest userRequest);

    UserResponse login(UserRequest userRequest);

    UserResponse delete(User user, DeleteRequest deleteRequest);
    UserResponse updatePassword(User user, PasswordRequest passwordRequest);

}
