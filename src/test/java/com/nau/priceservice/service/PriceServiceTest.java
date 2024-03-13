package com.nau.priceservice.service;

import com.nau.priceservice.data.dao.impl.PriceDaoImpl;
import com.nau.priceservice.data.entity.PriceEntity;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.service.impl.PriceServiceImpl;
import com.nau.priceservice.util.dto.PriceDto;
import com.nau.priceservice.util.mappers.PriceMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceDaoImpl priceDao;
    @Mock
    private PriceMapper dtoMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    void PriceService_GetAll_ReturnsTwoPriceDto() {
        PriceEntity priceEntity1 = new PriceEntity();
        PriceEntity priceEntity2 = new PriceEntity();
        PriceDto priceDto = new PriceDto();

        when(priceDao.findAll()).thenReturn(List.of(priceEntity1, priceEntity2));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        List<PriceDto> prices = priceService.getAll();

        Assertions.assertThat(prices)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void PriceService_Save_ReturnsPriceDto() throws InvalidPriceException {
        PriceDto priceDto = new PriceDto();
        priceDto.setId("1");
        priceDto.setProductId("101");
        priceDto.setCurrency("euro");
        priceDto.setUnitAmount(100);
        priceDto.setUnitAmountDecimal(100);
        priceDto.setPurchasePrice(80);
        priceDto.setSuggestedAmount(90);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId("1");
        priceEntity.setProductId("101");
        priceEntity.setCurrency("euro");
        priceEntity.setUnitAmount(100);
        priceEntity.setUnitAmountDecimal(100);
        priceEntity.setPurchasePrice(80);
        priceEntity.setSuggestedAmount(90);

        when(priceDao.save(Mockito.any(PriceEntity.class))).thenReturn(Optional.of(priceEntity));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(dtoMapper.mapFromDto(Mockito.any(PriceDto.class))).thenReturn(priceEntity);

        Optional<PriceDto> savedDto = priceService.save(priceDto);

        Assertions.assertThat(savedDto).isPresent();
        Assertions.assertThat(savedDto.get().getId()).isNotNull();
    }

    @Test
    void PriceService_Update_ReturnsUpdatedPriceDto() throws InvalidPriceException {
        PriceDto priceDto = new PriceDto();
        priceDto.setId("1");
        priceDto.setProductId("101");
        priceDto.setCurrency("euro");
        priceDto.setUnitAmount(100);
        priceDto.setUnitAmountDecimal(100);
        priceDto.setPurchasePrice(80);
        priceDto.setSuggestedAmount(90);
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId("1");
        priceEntity.setProductId("101");
        priceEntity.setCurrency("euro");
        priceEntity.setUnitAmount(100);
        priceEntity.setUnitAmountDecimal(100);
        priceEntity.setPurchasePrice(80);
        priceEntity.setSuggestedAmount(90);

        when(priceDao.findById(Mockito.any(String.class))).thenReturn(List.of(priceEntity));
        when(priceDao.update(Mockito.any(PriceEntity.class))).thenReturn(true);
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(dtoMapper.mapFromDto(Mockito.any(PriceDto.class))).thenReturn(priceEntity);

        PriceDto updatedPrice = priceService.update(priceDto);
        Assertions.assertThat(updatedPrice).isNotNull();
        Assertions.assertThat(updatedPrice.getId()).isEqualTo(priceDto.getId());
    }

    @Test
    void PriceService_Delete_ReturnsDeletedDto() throws InvalidPriceException {
        PriceDto priceDto = new PriceDto();
        priceDto.setId("1");
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId("1");

        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(priceDao.findById(Mockito.any(String.class))).thenReturn(List.of(priceEntity));
        when(priceDao.delete(Mockito.any(PriceEntity.class))).thenReturn(true);

        Optional<PriceDto> deletedPrice = priceService.delete(priceDto.getId());

        Assertions.assertThat(deletedPrice).isPresent();
        Assertions.assertThat(deletedPrice.get().getId()).isEqualTo(priceDto.getId());
    }

    @Test
    void PriceService_GetAllPricesOfProduct_ReturnsListOfDto() {
        PriceEntity priceEntity1 = new PriceEntity();
        priceEntity1.setProductId("101");
        PriceEntity priceEntity2 = new PriceEntity();
        priceEntity2.setProductId("101");
        PriceDto priceDto = new PriceDto();
        priceDto.setProductId("101");

        when(priceDao.getAllPricesOfProduct(Mockito.any(String.class))).thenReturn(List.of(priceEntity1, priceEntity2));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        List<PriceDto> prices = priceService.getAllPricesOfProduct("101");

        Assertions.assertThat(prices)
                .isNotEmpty()
                .hasSize(2);
        Assertions.assertThat(prices.get(0).getProductId()).isEqualTo("101");
        Assertions.assertThat(prices.get(1).getProductId()).isEqualTo("101");
    }

    @Test
    void PriceService_GetOnePriceOfProduct() throws InvalidPriceException {
        PriceDto priceDto = new PriceDto();
        priceDto.setId("1");
        priceDto.setProductId("101");
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId("1");
        priceEntity.setProductId("101");

        when(priceDao.getOnePriceOfProduct(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(priceEntity));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        Optional<PriceDto> foundPrice = priceService.getOnePriceOfProduct("1", "101");

        Assertions.assertThat(foundPrice).isPresent();
        Assertions.assertThat(foundPrice.get().getId()).isEqualTo("1");
        Assertions.assertThat(foundPrice.get().getProductId()).isEqualTo("101");
    }
}