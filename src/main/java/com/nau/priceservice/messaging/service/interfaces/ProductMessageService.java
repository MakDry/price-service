package com.nau.priceservice.messaging.service.interfaces;

import com.nau.priceservice.messaging.util.messages.ProductMessage;
import com.nau.priceservice.messaging.service.MessageService;

public interface ProductMessageService<T extends ProductMessage> extends MessageService<T> { }