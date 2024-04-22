package com.kaplich.calories.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleExceptionInternal() {
        // Arrange
        Exception ex = new Exception("Internal Server Error");
        Object body = null;
        HttpHeaders headers = new HttpHeaders();
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleExceptionInternal(ex, body, headers, statusCode, request);

        // Assert
        assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    void testHandleAllExceptions() {
        // Arrange
        Exception ex = new Exception("Some Error");
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(ex, request);

        // Assert\
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}