package com.nau.priceservice.exceptions;

import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {InvalidProductException.class, InvalidPriceException.class})
    public ResponseEntity<Object> handleInvalidDtoException(InvalidDtoException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AppResponseException responseException = new AppResponseException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(responseException, badRequest);
    }
}
