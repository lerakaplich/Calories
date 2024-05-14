package com.kaplich.calories.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Logging {

    @Pointcut("within(com.kaplich.calories.service.*)")
    public void serviceMethods() { }

    @Before("serviceMethods()")
    public void beforeAdvice(final JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());
        logArguments(joinPoint);
    }

    @AfterReturning(
            value = "serviceMethods()",
            returning = "result")
    public void afterReturningAdvice(
            final JoinPoint joinPoint, final Object result) {
        log.info(
                "Exiting method: {}, with result: {}",
                joinPoint.getSignature().getName(), result
        );
    }

    @After("serviceMethods()")
    public void afterAdvice(final JoinPoint joinPoint) {
        log.info("Method: {} has completed",
                joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "serviceMethods()",
            throwing = "ex")
    public void afterThrowingAdvice(final JoinPoint joinPoint,
                                    final Throwable ex) {
        log.error("Exception thrown in method: {} - Error: {}",
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(), ex);
    }

    void logArguments(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            StringBuilder argsString = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    argsString.append(args[i]);
                    if (i < args.length - 1) {
                        argsString.append(", ");
                    }
                }
            }
            log.info("Method arguments: {}", argsString);
        } else {
            log.info("Method arguments: {}", "null");
        }
    }
}

