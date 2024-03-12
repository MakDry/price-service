package com.nau.priceservice.module.product.impl;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.product.ProductCommand;
import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.module.product.interfaces.ProductCommandHandler;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandHandlerImpl implements ProductCommandHandler<ProductDto, ProductCommand> {

    @Autowired
    private ProductService<ProductDto> productService;

    @Override
    public ProductDto handleCreate(ProductCommand productCommand) throws InvalidProductException {
        ProductDto productDto = new ProductDto();
        productDto.setExternalId(productCommand.getExternalId());
        productDto.setTitle(productCommand.getTitle());
        return productService.save(productDto).orElseThrow(InvalidProductException::new);
    }

    @Override
    public ProductDto handleUpdate(String id, ProductCommand productCommand) throws InvalidProductException {
        ProductDto productToUpdate = new ProductDto();
        productToUpdate.setId(id);
        productToUpdate.setTitle(productCommand.getTitle());
        return productService.update(productToUpdate);
    }

    @Override
    public ProductQuery handleDelete(String id) throws InvalidDtoException {
        ProductQuery productQuery = new ProductQuery();
        ProductDto productDto = productService.delete(id).get();
        productQuery.setId(productDto.getId());
        productQuery.setExternalId(productDto.getExternalId());
        return productQuery;
    }
}
