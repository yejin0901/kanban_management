package com.team8.kanban.global.exception.customException;

import com.team8.kanban.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {

    private ErrorCode errorCode;

    public NotAuthorizedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
