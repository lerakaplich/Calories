package com.kaplich.calories.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Validated
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>
        handleExceptionInternal(final Exception ex,
                                final Object body, final HttpHeaders headers,
                                final HttpStatusCode statusCode,
                                final WebRequest request) {
        HttpErrorMessage errorMessage = new HttpErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(
            final Exception ex, final WebRequest request) {
       HttpErrorMessage errorMessage = new HttpErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


