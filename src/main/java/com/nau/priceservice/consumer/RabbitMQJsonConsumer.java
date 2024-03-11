package com.nau.priceservice.consumer;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.interfaces.ProductCommandHandler;
import com.nau.priceservice.util.dto.ProductDto;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    @Autowired
    private ProductCommandHandler<ProductDto, ProductCommand> productCommandHandler;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonProductMassage(ProductCommand productCommand) {
        logger.info("In class {} method consumeJsonProductMassage() received JSON message -> {}",
                RabbitMQJsonConsumer.class.getSimpleName(), productCommand);
        productCommandHandler.handleCreate(productCommand);
    }
}
