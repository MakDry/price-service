package com.nau.priceservice.module;

public interface CommandHandler<T, K> {
    T handleCreate(K k);

    T handleUpdate(String id, K k);

    String handleDelete(String id);
}
