package com.example.cambium.Exception;

public class InsertionFailedException extends RuntimeException {
    public InsertionFailedException(String msg) {
        super(msg);
    }
}
