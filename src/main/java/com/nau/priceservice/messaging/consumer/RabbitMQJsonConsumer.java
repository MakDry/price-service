package com.nau.priceservice.messaging.consumer;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.messaging.service.MessageService;
import com.nau.priceservice.messaging.util.messages.ProductMessage;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    @Autowired
    private MessageService<ProductMessage> messageService;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonProductMassage(ProductMessage productMessage) {
        logger.info("In class {} method consumeJsonProductMassage() received JSON message -> {}",
                RabbitMQJsonConsumer.class.getSimpleName(), productMessage);
        productMessage.getEvent().handleEvent(productMessage, messageService);
    }
}
