package com.nau.priceservice.exceptions.product;

import com.nau.priceservice.exceptions.InvalidDtoException;

public class InvalidProductException extends InvalidDtoException {
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
