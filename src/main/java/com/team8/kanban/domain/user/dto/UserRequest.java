package com.team8.kanban.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
}
