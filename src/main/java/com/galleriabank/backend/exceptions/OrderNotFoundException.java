package com.galleriabank.backend.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException() {
        super("Order not found");
    }
}
