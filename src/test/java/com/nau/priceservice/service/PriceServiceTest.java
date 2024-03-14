package com.nau.priceservice.service;

import com.nau.priceservice.data.dao.interfaces.PriceRepository;
import com.nau.priceservice.data.entity.PriceEntity;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.service.impl.PriceServiceImpl;
import com.nau.priceservice.util.dto.PriceDto;
import com.nau.priceservice.util.mappers.PriceMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private PriceRepository priceDao;
    @Mock
    private PriceMapper dtoMapper;
    @InjectMocks
    private PriceServiceImpl priceService;

    private PriceDto priceDto;
    private PriceEntity priceEntity;

    @BeforeEach
    void setUp() {
        priceDto = new PriceDto();
        priceDto.setId("1");
        priceDto.setProductId("101");
        priceDto.setCurrency("euro");
        priceDto.setUnitAmount(100);
        priceDto.setUnitAmountDecimal(100);
        priceDto.setPurchasePrice(80);
        priceDto.setSuggestedAmount(90);
        priceEntity = new PriceEntity();
        priceEntity.setId("1");
        priceEntity.setProductId("101");
        priceEntity.setCurrency("euro");
        priceEntity.setUnitAmount(100);
        priceEntity.setUnitAmountDecimal(100);
        priceEntity.setPurchasePrice(80);
        priceEntity.setSuggestedAmount(90);
    }

    @Test
    void PriceService_GetAll_ReturnsTwoPriceDto() {
        when(priceDao.findAll()).thenReturn(List.of(priceEntity, priceEntity));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        List<PriceDto> prices = priceService.getAll();

        Assertions.assertThat(prices)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void PriceService_save_ReturnsPriceDto() throws InvalidPriceException {
        when(priceDao.save(Mockito.any(PriceEntity.class))).thenReturn(priceEntity);
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(dtoMapper.mapFromDto(Mockito.any(PriceDto.class))).thenReturn(priceEntity);

        Optional<PriceDto> savedDto = priceService.save(priceDto);

        Assertions.assertThat(savedDto).isPresent();
        Assertions.assertThat(savedDto.get().getId()).isNotNull();
    }

    @Test
    void PriceService_save_DtoWithoutProductIdReturnsException() {
        priceDto.setProductId("");
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_save_DtoWithoutCurrencyReturnsException() {
        priceDto.setCurrency("");
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_save_DtoWithoutUnitAmountReturnsException() {
        priceDto.setUnitAmount(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_save_DtoWithoutUnitAmountDecimalReturnsException() {
        priceDto.setUnitAmountDecimal(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_save_DtoWithoutPurchasePriceReturnsException() {
        priceDto.setPurchasePrice(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_save_DtoWithoutSuggestedAmountReturnsException() {
        priceDto.setSuggestedAmount(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.save(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to save object");
    }

    @Test
    void PriceService_update_ReturnsUpdatedPriceDto() throws InvalidPriceException {
        when(priceDao.findById(Mockito.any(String.class))).thenReturn(Optional.of(priceEntity));
        when(priceDao.save(Mockito.any(PriceEntity.class))).thenReturn(priceEntity);
        when(priceDao.existsById(Mockito.anyString())).thenReturn(true);
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(dtoMapper.mapFromDto(Mockito.any(PriceDto.class))).thenReturn(priceEntity);

        PriceDto updatedPrice = priceService.update(priceDto);
        Assertions.assertThat(updatedPrice).isNotNull();
        Assertions.assertThat(updatedPrice.getId()).isEqualTo(priceDto.getId());
    }

    @Test
    void PriceService_update_DtoWithoutIdReturnsException() {
        priceDto.setId("");
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithoutCurrencyReturnsException() {
        priceDto.setCurrency("");
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithoutUnitAmountReturnsException() {
        priceDto.setUnitAmount(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithoutUnitAmountDecimalReturnsException() {
        priceDto.setUnitAmountDecimal(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithoutPurchasePriceReturnsException() {
        priceDto.setPurchasePrice(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithoutSuggestedAmountReturnsException() {
        priceDto.setSuggestedAmount(0);
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all PriceDto fields have been filled in to update object");
    }

    @Test
    void PriceService_update_DtoWithNonexistentIdReturnsException() {
        when(priceDao.existsById(Mockito.anyString())).thenReturn(false);
        when(dtoMapper.mapFromDto(Mockito.any(PriceDto.class))).thenReturn(priceEntity);

        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.update(priceDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("No suitable Price entity was found to update");
    }

    @Test
    void PriceService_delete_ReturnsDeletedDto() throws InvalidPriceException {
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);
        when(priceDao.findById(Mockito.any(String.class))).thenReturn(Optional.of(priceEntity));

        Optional<PriceDto> deletedPrice = priceService.delete(priceDto.getId());

        Assertions.assertThat(deletedPrice).isPresent();
        Assertions.assertThat(deletedPrice.get().getId()).isEqualTo(priceDto.getId());
    }

    @Test
    void PriceService_delete_DtoWithNonexistentIdReturnsException() {
        when(priceDao.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        InvalidPriceException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidPriceException.class, () ->{
            priceService.delete(priceDto.getId());
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("No suitable Price entity was found to delete");
    }

    @Test
    void PriceService_getAllPricesOfProduct_ReturnsListOfDto() {
        PriceEntity priceEntity2 = new PriceEntity();
        priceEntity2.setProductId("101");

        when(priceDao.findAllByProductId(Mockito.any(String.class))).thenReturn(List.of(priceEntity, priceEntity2));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        List<PriceDto> prices = priceService.getAllPricesOfProduct("101");

        Assertions.assertThat(prices)
                .isNotEmpty()
                .hasSize(2);
        Assertions.assertThat(prices.get(0).getProductId()).isEqualTo("101");
        Assertions.assertThat(prices.get(1).getProductId()).isEqualTo("101");
    }

    @Test
    void PriceService_getOnePriceOfProduct() throws InvalidPriceException {
        when(priceDao.findByIdAndProductId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(priceEntity));
        when(dtoMapper.mapToDto(Mockito.any(PriceEntity.class))).thenReturn(priceDto);

        Optional<PriceDto> foundPrice = priceService.getOnePriceOfProduct("1", "101");

        Assertions.assertThat(foundPrice).isPresent();
        Assertions.assertThat(foundPrice.get().getId()).isEqualTo("1");
        Assertions.assertThat(foundPrice.get().getProductId()).isEqualTo("101");
    }
}