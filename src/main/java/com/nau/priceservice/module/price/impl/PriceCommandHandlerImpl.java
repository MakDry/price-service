package com.nau.priceservice.module.price.impl;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.module.price.interfaces.PriceCommandHandler;
import com.nau.priceservice.service.interfaces.PriceService;
import com.nau.priceservice.util.dto.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceCommandHandlerImpl implements PriceCommandHandler<PriceDto, PriceCommand> {

    @Autowired
    private PriceService<PriceDto> priceService;

    @Override
    public PriceDto handleCreate(PriceCommand priceCommand, String productId) throws InvalidPriceException {
        PriceDto priceDto = buildPriceDto(priceCommand);
        priceDto.setProductId(productId);
        return priceService.save(priceDto).get();
    }

    @Override
    public PriceDto handleUpdate(String id, PriceCommand priceCommand) throws InvalidPriceException {
        PriceDto priceToUpdate = buildPriceDto(priceCommand);
        priceToUpdate.setId(id);
        return priceService.update(priceToUpdate);
    }

    @Override
    public String handleDelete(String id) throws InvalidDtoException {
        return priceService.delete(id).get().getId();
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
