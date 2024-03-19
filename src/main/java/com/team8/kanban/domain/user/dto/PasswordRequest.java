package com.team8.kanban.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String checkPassword;
}
