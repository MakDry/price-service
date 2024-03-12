package com.nau.priceservice.controller;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.price.PriceCommand;
import com.nau.priceservice.module.price.PriceQuery;
import com.nau.priceservice.module.price.interfaces.PriceCommandHandler;
import com.nau.priceservice.module.price.interfaces.PriceQueryHandler;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.module.product.interfaces.ProductCommandHandler;
import com.nau.priceservice.module.product.interfaces.ProductQueryHandler;
import com.nau.priceservice.util.dto.PriceDto;
import com.nau.priceservice.util.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductQueryHandler<ProductDto, ProductQuery> productQueryHandler;
    private PriceQueryHandler<PriceDto, PriceQuery> priceQueryHandler;
    private ProductCommandHandler<ProductDto, ProductCommand> productCommandHandler;
    private PriceCommandHandler<PriceDto, PriceCommand> priceCommandHandler;

    @Autowired
    public ProductController(ProductQueryHandler<ProductDto, ProductQuery> productQueryHandler,
                             PriceQueryHandler<PriceDto, PriceQuery> priceQueryHandler,
                             ProductCommandHandler<ProductDto, ProductCommand> productCommandHandler,
                             PriceCommandHandler<PriceDto, PriceCommand> priceCommandHandler) {
        this.productQueryHandler = productQueryHandler;
        this.priceQueryHandler = priceQueryHandler;
        this.productCommandHandler = productCommandHandler;
        this.priceCommandHandler = priceCommandHandler;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productQueryHandler.getAll();
    }

    @GetMapping(params = {"p"})
    public boolean isProductExists(@RequestParam(name = "p") String productId) {
        return productQueryHandler.isExists(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductCommand productCommand) throws InvalidProductException {
        return productCommandHandler.handleCreate(productCommand);
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto updateProduct(@PathVariable String productId, @RequestBody ProductCommand productCommand)
            throws InvalidDtoException {
        return productCommandHandler.handleUpdate(productId, productCommand);
    }

    @DeleteMapping("/{productId}")
    public ProductQuery deleteProduct(@PathVariable String productId) throws InvalidDtoException {
        return productCommandHandler.handleDelete(productId);
    }

    @GetMapping("/prices")
    public List<PriceDto> getAllPricesOfProduct(@RequestParam(name = "p", required = false) String productId) {
        return productId == null ? priceQueryHandler.getAll() : priceQueryHandler.getAllPricesOfProduct(productId);
    }

    @GetMapping("/{productId}/prices/{priceId}")
    public PriceDto getOnePriceOfProduct(@PathVariable String priceId, @PathVariable String productId) {
        return priceQueryHandler.getOnePriceOfProduct(priceId, productId);
    }

    @PostMapping("/prices/{priceId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto updatePrice(@PathVariable String priceId, @RequestBody PriceCommand priceCommand)
            throws InvalidDtoException {
        return priceCommandHandler.handleUpdate(priceId, priceCommand);
    }

    @DeleteMapping("/prices/{priceId}")
    public String deletePrice(@PathVariable String priceId) throws InvalidDtoException {
        return priceCommandHandler.handleDelete(priceId);
    }

    @PostMapping("/{productId}/prices")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto createPrice(@PathVariable String productId, @RequestBody PriceCommand priceCommand)
            throws InvalidPriceException {
        return priceCommandHandler.handleCreate(priceCommand, productId);
    }
}
