package com.nau.priceservice.data.dao.interfaces;

import com.nau.priceservice.data.dao.Dao;
import com.nau.priceservice.data.entity.PriceEntity;

import java.util.List;
import java.util.Optional;

public interface PriceDao<T extends PriceEntity, K extends String> extends Dao<T, K> {
    List<T> findById(K k);

    List<T> getAllPricesOfProduct(K k);

    Optional<T> getOnePriceOfProduct(K k, String productId);
}
