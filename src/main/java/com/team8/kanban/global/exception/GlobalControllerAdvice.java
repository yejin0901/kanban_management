package com.team8.kanban.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<String>> handleIllegalArgumentExceptions(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("url: {}, 메세지: {} \n stacktrace: {}", request.getRequestURI(), ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.badRequest().body(new CommonResponse<String>(ex.getMessage()));
    }
}
