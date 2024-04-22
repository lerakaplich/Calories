package com.kaplich.calories.exception;

import com.kaplich.calories.exception.GlobalExceptionHandler;
import com.kaplich.calories.exception.HttpErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        HttpErrorMessage expectedErrorMessage = new HttpErrorMessage(ex.getMessage());

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleExceptionInternal(ex, body, headers, statusCode, request);
        HttpErrorMessage actualErrorMessage = (HttpErrorMessage) response.getBody();

        // Assert
        //assertEquals(expectedErrorMessage, actualErrorMessage);
        assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    void testHandleAllExceptions() {
        // Arrange
        Exception ex = new Exception("Some Error");
        WebRequest request = mock(WebRequest.class);
        HttpErrorMessage expectedErrorMessage = new HttpErrorMessage(ex.getMessage());

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(ex, request);
        HttpErrorMessage actualErrorMessage = (HttpErrorMessage) response.getBody();

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        // Assert
        //assertEquals(expectedErrorMessage, actualErrorMessage);
        assertEquals(statusCode, response.getStatusCode());
    }
}