package com.nau.priceservice.service.interfaces;

import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.exceptions.price.InvalidProductException;
import com.nau.priceservice.service.Service;
import com.nau.priceservice.util.dto.ProductDto;

import java.util.Optional;

public interface ProductService<T extends ProductDto> extends Service<T> {
    Optional<T> save(T t) throws InvalidProductException;

    boolean update(T t) throws InvalidPriceException;

    Optional<T> delete(String id) throws InvalidProductException;

    boolean isExists(String id);
}
