package com.kaplich.calories.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleExceptionInternal() {
        Exception exception = new Exception("Test exception");
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = exceptionHandler.handleExceptionInternal(exception, null, headers, status, request);

        assertNotNull(response);
        assertEquals(status, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof HttpErrorMessage);
        HttpErrorMessage errorMessage = (HttpErrorMessage) response.getBody();
        assertEquals(exception.getMessage(), errorMessage.getMessage());
    }
}