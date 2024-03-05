package com.nau.priceservice.service.interfaces;

import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.service.Service;

import java.util.Optional;

public interface PriceService<T> extends Service<T> {
    Optional<T> save(T t) throws InvalidPriceException;
    boolean update(T t) throws InvalidPriceException;
    Optional<T> delete(String id) throws InvalidPriceException;
}
