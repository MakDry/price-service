package com.nau.priceservice.exceptions.price;

import com.nau.priceservice.exceptions.InvalidDtoException;

public class InvalidPriceException extends InvalidDtoException {

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
