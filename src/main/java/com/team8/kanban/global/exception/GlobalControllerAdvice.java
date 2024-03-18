package com.team8.kanban.global.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, IllegalAccessException.class})
    public ResponseEntity<CommonResponse<?>> handleException(IllegalArgumentException ex) {
        CommonResponse<?> Exception = new CommonResponse<>(ex.getMessage());
        return new ResponseEntity<>(
                Exception,
                HttpStatus.BAD_REQUEST
        );
    }
}
