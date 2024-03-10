package com.nau.priceservice.controller;

import com.mongodb.lang.Nullable;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.module.price.interfaces.PriceCommandHandler;
import com.nau.priceservice.module.price.interfaces.PriceQueryHandler;
import com.nau.priceservice.util.dto.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PriceController {

    private PriceQueryHandler<PriceDto, String> queryHandler;
    private PriceCommandHandler<PriceDto, PriceCommand> commandHandler;

    @Autowired
    public PriceController(PriceQueryHandler<PriceDto, String> queryHandler,
                           PriceCommandHandler<PriceDto, PriceCommand> commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @GetMapping("/prices")
    public List<PriceDto> getPrices(@RequestParam @Nullable String p) {
        return p == null ? queryHandler.getAll() : queryHandler.getById(p);
    }

    @PostMapping("/price")
    public PriceDto createPrice(@RequestBody PriceCommand priceCommand) {
        return commandHandler.handleCreate(priceCommand);
    }

    @PostMapping("/price/{p}")
    public PriceDto updatePrice(@PathVariable String p, @RequestBody PriceCommand priceCommand) {
        return commandHandler.handleUpdate(p, priceCommand);
    }

    @DeleteMapping("/price/{p}")
    public String deletePrice(@PathVariable String p) {
        return commandHandler.handleDelete(p);
    }
}
