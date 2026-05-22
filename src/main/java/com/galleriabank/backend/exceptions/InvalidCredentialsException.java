package com.galleriabank.backend.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid login or password");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}