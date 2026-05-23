package com.galleriabank.backend.exceptions;

public class ProductDeletedException extends RuntimeException {
    public ProductDeletedException(String message) {
        super(message);
    }
    public ProductDeletedException() {
        super("This product has been deleted");
    }
}
