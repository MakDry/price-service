package com.nau.priceservice.service.interfaces;

import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.service.Service;
import com.nau.priceservice.util.dto.ProductDto;

import java.util.Optional;

public interface ProductService<T extends ProductDto> extends Service<T> {
    Optional<T> save(T t) throws InvalidProductException;

    T update(T t) throws InvalidProductException;

    boolean isExists(String id);
}
