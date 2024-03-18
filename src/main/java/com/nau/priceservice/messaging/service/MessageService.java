package com.nau.priceservice.messaging.service;

public interface MessageService<T> {
    void create(T message);

    void update(T message);

    void delete(T message);
}
