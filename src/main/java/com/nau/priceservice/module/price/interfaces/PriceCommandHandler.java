package com.nau.priceservice.module.price.interfaces;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.CommandHandler;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.util.dto.PriceDto;

public interface PriceCommandHandler<T extends PriceDto, K extends PriceCommand> extends CommandHandler<T, K> {
    T handleCreate(K k, String productId) throws InvalidPriceException;

    String handleDelete(String id) throws InvalidDtoException;
}
