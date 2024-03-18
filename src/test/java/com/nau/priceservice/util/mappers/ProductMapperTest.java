package com.nau.priceservice.util.mappers;

import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.util.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMapperTest {
    private DtoMapper<ProductDto, ProductEntity> productMapper = new ProductMapperImpl();
    private ProductDto productDto;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setId("1");
        productDto.setExternalId("101");
        productDto.setTitle("Apples");
        productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setExternalId("101");
        productEntity.setTitle("Apples");
    }

    @Test
    void ProductMapper_mapToDto_ReturnsDtoFromEntityWithSameFields() {
        ProductDto mappedEntity = productMapper.mapToDto(productEntity);

        Assertions.assertThat(mappedEntity).isNotNull();
        Assertions.assertThat(mappedEntity.getId()).isEqualTo(productEntity.getId());
        Assertions.assertThat(mappedEntity.getExternalId()).isEqualTo(productEntity.getExternalId());
        Assertions.assertThat(mappedEntity.getTitle()).isEqualTo(productEntity.getTitle());
    }

    @Test
    void ProductMapper_mapFromDto_ReturnsEntityFromDtoWithSameFields() {
        ProductEntity mappedDto = productMapper.mapFromDto(productDto);
        Assertions.assertThat(mappedDto).isNotNull();
        Assertions.assertThat(mappedDto.getId()).isEqualTo(productDto.getId());
        Assertions.assertThat(mappedDto.getExternalId()).isEqualTo(productDto.getExternalId());
        Assertions.assertThat(mappedDto.getTitle()).isEqualTo(productDto.getTitle());
    }
}
