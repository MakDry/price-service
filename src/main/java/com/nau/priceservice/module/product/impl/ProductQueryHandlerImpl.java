package com.nau.priceservice.module.product.impl;

import com.nau.priceservice.module.product.ProductQuery;
import com.nau.priceservice.module.product.interfaces.ProductQueryHandler;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQueryHandlerImpl implements ProductQueryHandler<ProductDto, ProductQuery> {

    @Autowired
    private ProductService<ProductDto> productService;

    @Override
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @Override
    public boolean isExists(String id) {
        return productService.isExists(id);
    }
}
