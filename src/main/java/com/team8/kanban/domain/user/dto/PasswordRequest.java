package com.team8.kanban.domain.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String checkPassword;
}
