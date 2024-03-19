package com.team8.kanban.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResponse {
    private int statusCode;
    private HttpStatus state;
}
