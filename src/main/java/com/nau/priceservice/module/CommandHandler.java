package com.nau.priceservice.module;

import com.nau.priceservice.exceptions.InvalidDtoException;

public interface CommandHandler<T, K> {
    T handleUpdate(String id, K k) throws InvalidDtoException;
}
