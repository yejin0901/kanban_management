package com.team8.kanban.global.common;

import com.team8.kanban.global.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {
    private String msg;
    private T data;
    public CommonResponse(String msg) {
        this.msg = msg;
    }

    //custom errorcode 처리
    public static CommonResponse<String> fromErrorCode(ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getHttpStatus().toString(), errorCode.getMessage());
    }

}
