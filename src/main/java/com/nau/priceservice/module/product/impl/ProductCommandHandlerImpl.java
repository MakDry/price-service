package com.nau.priceservice.module.product.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.module.product.interfaces.ProductCommandHandler;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandHandlerImpl implements ProductCommandHandler<ProductDto, ProductCommand> {

    @Autowired
    private ProductService<ProductDto> productService;
    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    @Override
    public ProductDto handleCreate(ProductCommand productCommand) {
        ProductDto productDto = new ProductDto();
        productDto.setExternalId(productCommand.getExternalId());
        productDto.setTitle(productCommand.getTitle());
        try {
            productDto = productService.save(productDto).orElseThrow(InvalidProductException::new);
        } catch (InvalidProductException e) {
            logger.error("In class {} method handleCreate() couldn't save next object: {}, and get next:\n{}",
                    ProductCommandHandlerImpl.class.getSimpleName(), productDto, e);
        }
        return productDto;
    }

    @Override
    public ProductDto handleUpdate(String id, ProductCommand productCommand) {
        ProductDto productToUpdate = new ProductDto();
        productToUpdate.setId(id);
        productToUpdate.setTitle(productCommand.getTitle());
        try {
            productToUpdate = productService.update(productToUpdate);
        } catch (InvalidProductException e) {
            logger.error("In class {} method handleUpdate() couldn't update next object: {}, and get next:\n{}",
                    ProductCommandHandlerImpl.class.getSimpleName(), productToUpdate, e);
        }
        return productToUpdate;
    }

    @Override
    public ProductQuery handleDelete(String id) {
        ProductQuery productQuery = new ProductQuery();
        ProductDto productDto = new ProductDto();
        try {
            productDto = productService.delete(id).orElseThrow(IllegalArgumentException::new);
        } catch (InvalidDtoException | IllegalArgumentException e) {
            logger.error("In class {} method handleDelete() couldn't delete object wit id {}, and get next:\n{}",
                    ProductCommandHandlerImpl.class.getSimpleName(), id, e);
        }
        productQuery.setId(productDto.getId());
        productQuery.setExternalId(productDto.getExternalId());
        return productQuery;
    }
}
