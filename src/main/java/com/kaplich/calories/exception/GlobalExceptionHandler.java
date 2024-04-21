package com.kaplich.calories.exception;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Validated
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object>
        handleExceptionInternal(Exception ex,
                                Object body, HttpHeaders headers,
                                HttpStatusCode statusCode, WebRequest request) {
        HttpErrorMessage errorMessage = new HttpErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }
}
