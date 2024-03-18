package com.nau.priceservice.module.product.interfaces;

import com.nau.priceservice.module.QueryHandler;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.util.dto.ProductDto;

public interface ProductQueryHandler<T extends ProductDto, K extends ProductQuery> extends QueryHandler<T, K> {
    boolean isExists(String id);
}
