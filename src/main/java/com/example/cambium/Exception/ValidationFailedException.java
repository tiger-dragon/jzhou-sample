package com.example.cambium.Exception;


//Just a sample custom exception. We use this to demonstrate how to implement
// an exception handling controller
public class ValidationFailedException extends RuntimeException{
    public ValidationFailedException(String message) {
        super(message);
    }
}
