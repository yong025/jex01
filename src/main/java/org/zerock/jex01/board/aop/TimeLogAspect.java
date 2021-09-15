package org.zerock.jex01.board.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class TimeLogAspect {

    { //default 블럭
        log.info("TimeLogAspect..............................");
        log.info("TimeLogAspect..............................");
        log.info("TimeLogAspect..............................");
        log.info("TimeLogAspect..............................");
        log.info("TimeLogAspect..............................");
    }

    @Before("execution(* org.zerock.jex01.board.service.*.*(..))")// 여기서 *는 접근제한자
    public void logBefore(JoinPoint joinPoint) {
        log.info("log before..........................");

        log.info(joinPoint.getTarget()); //실제 객체를 가져오는 메서드
        log.info(Arrays.toString(joinPoint.getArgs()));

        log.info("log before..........................end");
    }

    @AfterReturning("execution(* org.zerock.jex01.board.service.*.*(..))")// 여기서 *는 접근제한자
    public void logAfter() {
        log.info("log afterReturning..........................");
    }
}
