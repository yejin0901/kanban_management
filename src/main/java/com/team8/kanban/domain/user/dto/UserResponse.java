package com.team8.kanban.domain.user.dto;

import com.team8.kanban.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private String username;

    public UserResponse(User user) {
        this.username = user.getUsername();
    }
}
