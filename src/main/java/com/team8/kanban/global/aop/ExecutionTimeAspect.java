package com.team8.kanban.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "메소드 실행시간 측정")
public class ExecutionTimeAspect {
    @Pointcut("execution(* com.team8.kanban.domain.board.controller.BoardController.*(..))")
    public void boardControllerMethods() {}

    @Pointcut("execution(* com.team8.kanban.domain.user.UserController.*(..))")
    public void userControllerMethods() {}

    @Pointcut("execution(* com.team8.kanban.domain.comment.CommentController.*(..))")
    public void commentControllerMethods() {}

    @Pointcut("execution(* com.team8.kanban.domain.card.controller.CardController.*(..))")
    public void cardControllerMethods() {}

    @Pointcut("execution(* com.team8.kanban.domain.section.SectionController.*(..))")
    public void sectionControllerMethods() {}

    @Pointcut("execution(* com.team8.kanban.domain.board.controller.BoardUserController*(..))")
    public void boardUserControllerMethods() {
    }

    @Around("boardControllerMethods() || " +
            "userControllerMethods() || " +
            "commentControllerMethods() || " +
            "cardControllerMethods() || " +
        "sectionControllerMethods() ||" +
        "boardUserControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 대상 메소드 호출
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            log.info("Method: {}, execution time: {} m/s ",joinPoint.getSignature().getName(),executionTime);
        }
    }

}

