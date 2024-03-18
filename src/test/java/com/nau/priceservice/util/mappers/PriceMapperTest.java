package com.nau.priceservice.util.mappers;

import com.nau.priceservice.data.entity.PriceEntity;
import com.nau.priceservice.util.dto.PriceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriceMapperTest {
    private DtoMapper<PriceDto, PriceEntity> priceMapper = new PriceMapperImpl();
    private PriceDto priceDto;
    private PriceEntity priceEntity;

    @BeforeEach
    void setUp() {
        priceDto = new PriceDto();
        priceDto.setId("1");
        priceDto.setProductId("101");
        priceDto.setCurrency("euro");
        priceDto.setUnitAmount(88);
        priceDto.setUnitAmountDecimal(88.5);
        priceDto.setPurchasePrice(60.5);
        priceDto.setSuggestedAmount(69.25);
        priceEntity = new PriceEntity();
        priceEntity.setId("1");
        priceEntity.setProductId("101");
        priceEntity.setCurrency("euro");
        priceEntity.setUnitAmount(88);
        priceEntity.setUnitAmountDecimal(88.5);
        priceEntity.setPurchasePrice(60.5);
        priceEntity.setSuggestedAmount(69.25);
    }

    @Test
    void PriceMapper_mapToDto_ReturnsDtoFromEntityWithSameFields() {
        PriceDto mappedEntity = priceMapper.mapToDto(priceEntity);

        Assertions.assertThat(mappedEntity).isNotNull();
        Assertions.assertThat(mappedEntity.getId()).isEqualTo(priceEntity.getId());
        Assertions.assertThat(mappedEntity.getProductId()).isEqualTo(priceEntity.getProductId());
        Assertions.assertThat(mappedEntity.getCurrency()).isEqualTo(priceEntity.getCurrency());
        Assertions.assertThat(mappedEntity.getUnitAmount()).isEqualTo(priceEntity.getUnitAmount());
        Assertions.assertThat(mappedEntity.getUnitAmountDecimal()).isEqualTo(priceEntity.getUnitAmountDecimal());
        Assertions.assertThat(mappedEntity.getPurchasePrice()).isEqualTo(priceEntity.getPurchasePrice());
        Assertions.assertThat(mappedEntity.getSuggestedAmount()).isEqualTo(priceEntity.getSuggestedAmount());
    }

    @Test
    void PriceMapper_mapFromDto_ReturnsEntityFromDtoWithSameFields() {
        PriceEntity mappedDto = priceMapper.mapFromDto(priceDto);

        Assertions.assertThat(mappedDto).isNotNull();
        Assertions.assertThat(mappedDto.getId()).isEqualTo(priceDto.getId());
        Assertions.assertThat(mappedDto.getProductId()).isEqualTo(priceDto.getProductId());
        Assertions.assertThat(mappedDto.getCurrency()).isEqualTo(priceDto.getCurrency());
        Assertions.assertThat(mappedDto.getUnitAmount()).isEqualTo(priceDto.getUnitAmount());
        Assertions.assertThat(mappedDto.getUnitAmountDecimal()).isEqualTo(priceDto.getUnitAmountDecimal());
        Assertions.assertThat(mappedDto.getPurchasePrice()).isEqualTo(priceDto.getPurchasePrice());
        Assertions.assertThat(mappedDto.getSuggestedAmount()).isEqualTo(priceDto.getSuggestedAmount());
    }
}
