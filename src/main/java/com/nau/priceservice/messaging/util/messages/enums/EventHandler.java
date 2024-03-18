package com.nau.priceservice.messaging.util.messages.enums;

import com.nau.priceservice.messaging.service.MessageService;

public interface EventHandler<T, K extends MessageService<T>> {
    void handleEvent(T message, K messageService);
}
