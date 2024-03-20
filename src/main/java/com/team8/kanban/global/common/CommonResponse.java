package com.team8.kanban.global.common;

import com.team8.kanban.global.exception.ErrorCode;
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
}
