package com.nau.priceservice.module.price.impl;

import com.nau.priceservice.module.price.interfaces.PriceQueryHandler;
import com.nau.priceservice.service.Service;
import com.nau.priceservice.util.dto.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceQueryHandlerImpl implements PriceQueryHandler<PriceDto, String> {

    @Autowired
    private Service<PriceDto> priceService;

    @Override
    public List<PriceDto> getById(String id) {
        return priceService.getById(id);
    }

    @Override
    public List<PriceDto> getAll() {
        return priceService.getAll();
    }
}
