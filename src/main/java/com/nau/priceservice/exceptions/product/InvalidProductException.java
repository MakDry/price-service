package com.nau.priceservice.exceptions.product;

public class InvalidProductException extends Throwable {
    public InvalidProductException() { }

    public InvalidProductException(String message) {
        super(message);
    }

    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProductException(Throwable cause) {
        super(cause);
    }
}
