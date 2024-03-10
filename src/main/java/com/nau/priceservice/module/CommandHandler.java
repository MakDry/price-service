package com.nau.priceservice.module;

public interface CommandHandler<T, K> {
    T handleUpdate(String id, K k);
}
