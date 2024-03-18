package com.nau.priceservice.module.product.interfaces;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.CommandHandler;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.util.dto.ProductDto;

public interface ProductCommandHandler<T extends ProductDto, K extends ProductCommand> extends CommandHandler<T, K> {
    T handleCreate(K k) throws InvalidProductException;

    ProductQuery handleDelete(String id) throws InvalidDtoException;
}
