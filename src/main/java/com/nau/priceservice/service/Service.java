package com.nau.priceservice.service;

import com.nau.priceservice.exceptions.InvalidDtoException;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> getAll();

    Optional<T> delete(String id) throws InvalidDtoException;
}
