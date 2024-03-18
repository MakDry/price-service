package com.nau.priceservice.module.price.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.price.PriceQuery;
import com.nau.priceservice.module.price.interfaces.PriceQueryHandler;
import com.nau.priceservice.service.interfaces.PriceService;
import com.nau.priceservice.util.dto.PriceDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceQueryHandlerImpl implements PriceQueryHandler<PriceDto, PriceQuery> {

    @Autowired
    private PriceService<PriceDto> priceService;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @Override
    public List<PriceDto> getAll() {
        return priceService.getAll();
    }

    @Override
    public List<PriceDto> getAllPricesOfProduct(String productId) {
        return priceService.getAllPricesOfProduct(productId);
    }

    @Override
    public PriceDto getOnePriceOfProduct(String priceId, String productId) {
        PriceDto price = new PriceDto();
        try {
            price = priceService.getOnePriceOfProduct(priceId, productId)
                    .orElseThrow(IllegalArgumentException::new);
        } catch (InvalidPriceException | IllegalArgumentException e) {
            logger.error("In class {} method getOnePriceOfProduct() couldn't find price with id {}," +
                            " and productId {}, and get next:\n{}",
                    PriceQueryHandlerImpl.class.getSimpleName(), priceId, productId, e);
        }
        return price;
    }
}
