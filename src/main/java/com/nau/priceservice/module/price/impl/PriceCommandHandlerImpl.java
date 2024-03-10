package com.nau.priceservice.module.price.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.module.price.interfaces.PriceCommandHandler;
import com.nau.priceservice.service.interfaces.PriceService;
import com.nau.priceservice.util.dto.PriceDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceCommandHandlerImpl implements PriceCommandHandler<PriceDto, PriceCommand> {

    @Autowired
    private PriceService<PriceDto> priceService;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @Override
    public PriceDto handleCreate(PriceCommand priceCommand, String productId) {
        PriceDto priceDto = buildPriceDto(priceCommand);
        priceDto.setProductId(productId);
        try {
            priceDto = priceService.save(priceDto).orElseThrow(InvalidPriceException::new);
        } catch (InvalidPriceException e) {
            logger.error("In class {} method handleCreate() couldn't save next object: {}, and get next:\n{}",
                    PriceCommandHandlerImpl.class.getSimpleName(), priceDto, e);
        }
        return priceDto;
    }

    @Override
    public PriceDto handleUpdate(String id, PriceCommand priceCommand) {
        PriceDto priceToUpdate = buildPriceDto(priceCommand);
        priceToUpdate.setId(id);
        try {
            priceToUpdate = priceService.update(priceToUpdate);
        } catch (InvalidPriceException e) {
            logger.error("In class {} method handleUpdate() couldn't update next object: {}, and get next:\n{}",
                    PriceCommandHandlerImpl.class.getSimpleName(), priceToUpdate, e);
        }
        return priceToUpdate;
    }

    @Override
    public String handleDelete(String id) {
        try {
            if (priceService.delete(id).isPresent()) {
                return id;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (InvalidDtoException | IllegalArgumentException e) {
            logger.error("In class {} method handleDelete() couldn't delete object wit id {}, and get next:\n{}",
                    PriceCommandHandlerImpl.class.getSimpleName(), id, e);
        }
        return "";
    }

    private PriceDto buildPriceDto(PriceCommand priceCommand) {
        PriceDto priceDto = new PriceDto();
        priceDto.setCurrency(priceCommand.getCurrency());
        priceDto.setUnitAmount(priceCommand.getUnitAmount());
        priceDto.setUnitAmountDecimal(priceCommand.getUnitAmountDecimal());
        priceDto.setPurchasePrice(priceCommand.getPurchasePrice());
        priceDto.setSuggestedAmount(calculateSuggestedAmount(priceCommand.getUnitAmountDecimal(),
                priceCommand.getPurchasePrice()));
        return priceDto;
    }

    // Calculation of the suggested price per product unit
    private double calculateSuggestedAmount(double unitAmountDecimal, double purchasePrice) {
        return (unitAmountDecimal + purchasePrice) / 2;
    }
}
