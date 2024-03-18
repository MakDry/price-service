package com.nau.priceservice.messaging.util.mappers;

public interface MessageMapper<T, K> {
    K mapFromMessage(T t);
}
