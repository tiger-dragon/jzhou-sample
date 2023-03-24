package com.example.cambium.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionHandlingController {
    // Add some specific exception handling functions here...

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponse validationFailed(ValidationFailedException ex) {
        ExceptionResponse resp = new ExceptionResponse("400 Bad Request", "Validation failed: " + ex.getMessage());
        return resp;
    }

    @ExceptionHandler(InsertionFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ExceptionResponse insertionFailed(InsertionFailedException ex) {
        ExceptionResponse resp = new ExceptionResponse("409 Conflict", "Insertion failed: " + ex.getMessage());
        return resp;
    }
}
