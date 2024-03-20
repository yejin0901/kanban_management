package com.team8.kanban.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //section
    SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Section not exists");

    //user


    //board


    //card


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}