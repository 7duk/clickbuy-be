package dev.sideproject.ndx2.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
public class LoggingMonitor {

    @Before("execution(* dev.sideproject.ndx2..*(..))")
    public void beforeStarting(JoinPoint joinPoint) {
        log.info("{} is starting with arguments {{}}",joinPoint.getSignature().toShortString(), Arrays.toString(joinPoint.getArgs()));
    }

}