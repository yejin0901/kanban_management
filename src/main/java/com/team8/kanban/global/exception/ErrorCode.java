package com.team8.kanban.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //section
    SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Section not exists"),

    //user

    //board
    BOARD_AUTHORIZED(HttpStatus.FORBIDDEN, "보드 접근 권한이 없습니다."),
    NOT_BOARD_CREATED_USER(HttpStatus.FORBIDDEN, "보드 생성 유저가 아닙니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 보드는 존재하지 않습니다."),
    BOARD_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다.");

    //card


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}