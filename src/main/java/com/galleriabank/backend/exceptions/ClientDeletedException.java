package com.galleriabank.backend.exceptions;

public class ClientDeletedException extends RuntimeException {
    public ClientDeletedException(String message) {
        super(message);
    }

    public ClientDeletedException() {
        super("This client has been deleted");
    }
}
