package com.nau.priceservice.util.mappers;

import com.nau.priceservice.data.entity.PriceEntity;
import com.nau.priceservice.service.impl.PriceServiceImpl;
import com.nau.priceservice.util.dto.PriceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PriceServiceImpl.class)
public interface PriceMapper extends DtoMapper<PriceDto, PriceEntity> {
    @Override
    PriceDto mapToDto(PriceEntity priceEntity);

    @Override
    PriceEntity mapFromDto(PriceDto priceDto);
}
