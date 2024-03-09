package com.nau.priceservice.util.mappers;

import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.service.impl.ProductServiceImpl;
import com.nau.priceservice.util.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductServiceImpl.class)
public interface ProductMapper extends DtoMapper<ProductDto, ProductEntity> {
    @Override
    ProductDto mapToDto(ProductEntity productEntity);

    @Override
    ProductEntity mapFromDto(ProductDto productDto);
}
