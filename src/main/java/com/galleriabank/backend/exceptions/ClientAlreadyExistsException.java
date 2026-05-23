package com.galleriabank.backend.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException() {
        super("Client already exists");
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
