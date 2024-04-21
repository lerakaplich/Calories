package com.kaplich.calories.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}
