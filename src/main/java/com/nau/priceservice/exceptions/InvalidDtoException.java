package com.nau.priceservice.exceptions;

public class InvalidDtoException extends Throwable {
    public InvalidDtoException() {
    }

    public InvalidDtoException(String message) {
        super(message);
    }

    public InvalidDtoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDtoException(Throwable cause) {
        super(cause);
    }
}
