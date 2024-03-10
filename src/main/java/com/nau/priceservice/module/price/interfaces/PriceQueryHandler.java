package com.nau.priceservice.module.price.interfaces;

import com.nau.priceservice.module.QueryHandler;
import com.nau.priceservice.module.price.PriceQuery;
import com.nau.priceservice.util.dto.PriceDto;

import java.util.List;

public interface PriceQueryHandler<T extends PriceDto, K extends PriceQuery> extends QueryHandler<T, K> {
    List<T> getAllPricesOfProduct(String productId);

    T getOnePriceOfProduct(String priceId, String productId);
}
