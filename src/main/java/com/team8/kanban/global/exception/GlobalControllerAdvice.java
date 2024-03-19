package com.team8.kanban.global.exception;

import com.team8.kanban.global.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalAccessException.class})
    public ResponseEntity<CommonResponse<?>> handleException(IllegalArgumentException ex) {
        CommonResponse<?> Exception = new CommonResponse<>(ex.getMessage());
        return new ResponseEntity<>(
                Exception,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NotFoundException.class})
    public CommonResponse<String> handleNotFound(NotFoundException ex) {
        return CommonResponse.fromErrorCode(ex.getErrorCode());
    }


}
