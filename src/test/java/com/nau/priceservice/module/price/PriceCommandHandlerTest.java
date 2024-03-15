package com.nau.priceservice.module.price;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.price.impl.PriceCommandHandlerImpl;
import com.nau.priceservice.service.interfaces.PriceService;
import com.nau.priceservice.util.dto.PriceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCommandHandlerTest {
    @Mock
    private PriceService<PriceDto> priceService;
    @InjectMocks
    PriceCommandHandlerImpl priceCommandHandler;

    private PriceDto priceDto;
    private PriceCommand priceCommand;

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
        priceCommand = new PriceCommand();
        priceCommand.setCurrency(priceDto.getCurrency());
        priceCommand.setUnitAmount(priceDto.getUnitAmount());
        priceCommand.setUnitAmountDecimal(priceDto.getUnitAmountDecimal());
        priceCommand.setPurchasePrice(priceDto.getPurchasePrice());
    }

    @Test
    void PriceCommandHandler_handleCreate_ReturnsSavedDtoWithSameFields() throws InvalidPriceException {
        when(priceService.save(Mockito.any(PriceDto.class))).thenReturn(Optional.of(priceDto));

        PriceDto savedPrice = priceCommandHandler.handleCreate(priceCommand, priceDto.getProductId());

        Assertions.assertThat(savedPrice).isNotNull();
        Assertions.assertThat(savedPrice.getId())
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(savedPrice.getProductId()).isEqualTo(priceDto.getProductId());
        Assertions.assertThat(savedPrice.getCurrency()).isEqualTo(priceDto.getCurrency());
        Assertions.assertThat(savedPrice.getUnitAmount()).isEqualTo(priceDto.getUnitAmount());
        Assertions.assertThat(savedPrice.getUnitAmountDecimal()).isEqualTo(priceDto.getUnitAmountDecimal());
        Assertions.assertThat(savedPrice.getPurchasePrice()).isEqualTo(priceDto.getPurchasePrice());
        Assertions.assertThat(savedPrice.getSuggestedAmount()).isEqualTo(priceDto.getSuggestedAmount());
    }

    @Test
    void PriceCommandHandler_handleUpdate_ReturnsDtoWithUpdatedFields() throws InvalidPriceException {
        when(priceService.update(Mockito.any(PriceDto.class))).thenReturn(priceDto);

        PriceDto updatedPrice = priceCommandHandler.handleUpdate(priceDto.getId(), priceCommand);

        Assertions.assertThat(updatedPrice).isNotNull();
        Assertions.assertThat(updatedPrice.getId()).isEqualTo(priceDto.getId());
        Assertions.assertThat(updatedPrice.getProductId()).isEqualTo(priceDto.getProductId());
        Assertions.assertThat(updatedPrice.getCurrency()).isEqualTo(priceCommand.getCurrency());
        Assertions.assertThat(updatedPrice.getUnitAmount()).isEqualTo(priceCommand.getUnitAmount());
        Assertions.assertThat(updatedPrice.getUnitAmountDecimal()).isEqualTo(priceCommand.getUnitAmountDecimal());
        Assertions.assertThat(updatedPrice.getPurchasePrice()).isEqualTo(priceCommand.getPurchasePrice());
        Assertions.assertThat(updatedPrice.getSuggestedAmount()).isEqualTo(priceDto.getSuggestedAmount());
    }

    @Test
    void PriceCommandHandler_handleDelete_ReturnsIdOfDeletedDto() throws InvalidDtoException {
        String id = "1";
        priceDto.setId(id);

        when(priceService.delete(id)).thenReturn(Optional.of(priceDto));

        String deletedDtoId = priceCommandHandler.handleDelete(id);

        Assertions.assertThat(deletedDtoId)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(id);
    }
}