package com.team8.kanban.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, IllegalAccessException.class})
    public ResponseEntity<CommonResponse<?>> handleException(IllegalArgumentException ex) {
        CommonResponse<?> Exception = new CommonResponse<>(ex.getMessage());
        return new ResponseEntity<>(
                Exception,
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponse<ExceptionResponse>> IllegalArgumentException(IllegalArgumentException ex) {
        log.info(">>>IllegalArgumentException<<<");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.<ExceptionResponse>builder()
                        .msg(ex.getMessage())
                        .data(createResponse(HttpStatus.BAD_REQUEST))
                        .build());

    }
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<CommonResponse<ExceptionResponse>> NullPointerException(NullPointerException ex) {
        log.info(">>>NullPointerException<<<");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.<ExceptionResponse>builder()
                        .msg(ex.getMessage())
                        .data(createResponse(HttpStatus.BAD_REQUEST))
                        .build());

    }
    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<CommonResponse<ExceptionResponse>> DuplicateKeyException(DuplicateKeyException ex) {
        log.info(">>>DuplicateKeyException<<<");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.<ExceptionResponse>builder()
                        .msg(ex.getMessage())
                        .data(createResponse(HttpStatus.BAD_REQUEST))
                        .build());

    }

    private ExceptionResponse createResponse(HttpStatus status) {
        return ExceptionResponse.builder()
                .statusCode(status.value())
                .state(status)
                .build();
    }
}
