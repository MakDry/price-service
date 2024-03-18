package com.nau.priceservice.messaging.util.mappers.interfaces;

import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.messaging.util.mappers.MessageMapper;
import com.nau.priceservice.messaging.util.messages.ProductMessage;

public interface ProductMessageMapper<T extends ProductMessage, K extends ProductEntity>
        extends MessageMapper<T, K> { }
