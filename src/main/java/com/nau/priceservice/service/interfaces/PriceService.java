package com.nau.priceservice.service.interfaces;

import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.service.Service;
import com.nau.priceservice.util.dto.PriceDto;

import java.util.List;
import java.util.Optional;

public interface PriceService<T extends PriceDto> extends Service<T> {
    List<T> getById(String id);

    Optional<T> save(T t) throws InvalidPriceException;

    boolean update(T t) throws InvalidPriceException;

    Optional<T> delete(String id) throws InvalidPriceException;

    List<T> getAllPricesOfProduct(String id);

    Optional<T> getOnePriceOfProduct(String id, String productId) throws InvalidPriceException;
}
