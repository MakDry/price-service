package com.nau.priceservice.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record AppResponseException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) { }
