package com.example.bogexercisems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExerciseException extends RuntimeException {

    public ExerciseException() {
    }

    public ExerciseException(String message) {
        super(message);
    }

    public ExerciseException(String message, Throwable cause) {
        super(message, cause);
    }
}
