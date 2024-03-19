package com.team8.kanban.global.exception;


import com.team8.kanban.global.exception.error.SectionErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{
   private final SectionErrorCode errorCode;
}
