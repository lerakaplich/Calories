package com.kaplich.calories.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class HttpErrorMessage {
    @JsonProperty("exception")
    private String message;
}
