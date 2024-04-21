package com.kaplich.calories.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpErrorMessageTest {

    private HttpErrorMessage errorMessage;

    @BeforeEach
    void setUp() {
        errorMessage = new HttpErrorMessage("Test message");
    }

    @Test
    void testGetMessage() {
        String message = errorMessage.getMessage();

        assertEquals("Test message", message);
    }

    @Test
    void testSetMessage() {
        errorMessage.setMessage("New message");

        assertEquals("New message", errorMessage.getMessage());
    }
}