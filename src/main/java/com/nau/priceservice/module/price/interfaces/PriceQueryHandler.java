package com.nau.priceservice.module.price.interfaces;

import com.nau.priceservice.module.QueryHandler;
import com.nau.priceservice.util.dto.PriceDto;

public interface PriceQueryHandler<T extends PriceDto, K extends String> extends QueryHandler<T, K> { }
