package com.nau.priceservice.messaging.util.messages.enums;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.messaging.service.MessageService;
import com.nau.priceservice.messaging.util.messages.ProductMessage;
import org.slf4j.LoggerFactory;

public enum ProductEvent implements EventHandler<ProductMessage, MessageService<ProductMessage>> {
    UNDETERMINED,
    CREATED {
        @Override
        public void handleEvent(ProductMessage message, MessageService<ProductMessage> productMessageService) {
            productMessageService.create(message);
        }
    },
    UPDATED {
        @Override
        public void handleEvent(ProductMessage message, MessageService<ProductMessage> productMessageService) {
            productMessageService.update(message);
        }
    },
    DELETED {
        @Override
        public void handleEvent(ProductMessage message, MessageService<ProductMessage> productMessageService) {
            productMessageService.delete(message);
        }
    };

    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @Override
    public void handleEvent(ProductMessage message, MessageService<ProductMessage> productMessageService) {
        logger.error("{} couldn't handle any event, because messages has no event type. Message -> {}",
                ProductEvent.class.getSimpleName(), message);
    }
}
