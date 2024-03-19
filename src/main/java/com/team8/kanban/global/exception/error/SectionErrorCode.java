package com.team8.kanban.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SectionErrorCode implements ErrorCode {

    SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Section not exists");

    private final HttpStatus httpStatus;
    private final String message;
}
