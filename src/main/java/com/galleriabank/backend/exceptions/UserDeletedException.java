package com.galleriabank.backend.exceptions;

public class UserDeletedException extends RuntimeException {

    public UserDeletedException(String message) {
        super(message);
    }

    public UserDeletedException() {
        super("This user has been deleted");
    }
}
