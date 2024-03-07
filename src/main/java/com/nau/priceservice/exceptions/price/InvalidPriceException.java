package com.nau.priceservice.exceptions.price;

public class InvalidPriceException extends Throwable {

    public InvalidPriceException() { }

    public InvalidPriceException(String message) {
        super(message);
    }

    public InvalidPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceException(Throwable cause) {
        super(cause);
    }
}
