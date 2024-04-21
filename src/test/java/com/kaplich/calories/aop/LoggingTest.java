package com.kaplich.calories.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class LoggingTest {

    @Mock
    private Logger logger;

    @Mock
    private JoinPoint joinPoint;

    private Logging logging;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        logging = new Logging();
    }

    @Test
    void testBeforeAdvice() {
        Signature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        logging.beforeAdvice(joinPoint);

    }

    @Test
    void testBeforeAdvice_NoArguments() {
        Signature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        logging.beforeAdvice(joinPoint);

    }

    @Test
    void testAfterReturningAdvice() {
        Signature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        Object result = "result";

        logging.afterReturningAdvice(joinPoint, result);

    }

    @Test
    void testAfterAdvice() {
        Signature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");

        logging.afterAdvice(joinPoint);

    }

    @Test
    void testAfterThrowingAdvice() {
        Signature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        Throwable exception = new RuntimeException("Test exception");

        logging.afterThrowingAdvice(joinPoint, exception);
    }
}