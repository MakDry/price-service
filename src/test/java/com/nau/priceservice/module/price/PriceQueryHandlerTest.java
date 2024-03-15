package com.nau.priceservice.module.price;

import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.module.price.impl.PriceQueryHandlerImpl;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceQueryHandlerTest {
    @Mock
    private PriceService<PriceDto> priceService;
    @InjectMocks
    PriceQueryHandlerImpl priceQueryHandler;

    private PriceDto priceDto1;
    private PriceDto priceDto2;

    @BeforeEach
    void setUp() {
        priceDto1 = new PriceDto();
        priceDto1.setId("1");
        priceDto1.setProductId("101");
        priceDto1.setCurrency("euro");
        priceDto1.setUnitAmount(100);
        priceDto1.setUnitAmountDecimal(100);
        priceDto1.setPurchasePrice(80);
        priceDto1.setSuggestedAmount(90);
        priceDto2 = new PriceDto();
        priceDto2.setId("2");
        priceDto2.setProductId("102");
        priceDto2.setCurrency("euro");
        priceDto2.setUnitAmount(90);
        priceDto2.setUnitAmountDecimal(90);
        priceDto2.setPurchasePrice(80);
        priceDto2.setSuggestedAmount(85);
    }

    @Test
    void PriceQueryHandler_getAll_ReturnsAllFoundDtos() {
        when(priceService.getAll()).thenReturn(List.of(priceDto1, priceDto2));

        List<PriceDto> foundPrices = priceQueryHandler.getAll();

        Assertions.assertThat(foundPrices)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void PriceQueryHandler_getAllPricesOfProduct_ReturnsAllPricesWithSameProductId() {
        priceDto2.setProductId("101");

        when(priceService.getAllPricesOfProduct(Mockito.anyString())).thenReturn(List.of(priceDto1, priceDto2));

        List<PriceDto> foundPrices = priceQueryHandler.getAllPricesOfProduct("101");

        Assertions.assertThat(foundPrices)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
        Assertions.assertThat(foundPrices.get(0).getProductId()).isEqualTo(priceDto1.getProductId());
        Assertions.assertThat(foundPrices.get(1).getProductId()).isEqualTo(priceDto2.getProductId());
    }

    @Test
    void PriceQueryHandler_getOnePRiceOfProduct_ReturnsDtoWithNeededFields() throws InvalidPriceException {
        when(priceService.getOnePriceOfProduct(priceDto1.getId(), priceDto1.getProductId())).thenReturn(Optional.of(priceDto1));

        PriceDto foundPrice = priceQueryHandler.getOnePriceOfProduct(priceDto1.getId(), priceDto1.getProductId());

        Assertions.assertThat(foundPrice).isNotNull();
        Assertions.assertThat(foundPrice.getId()).isEqualTo(priceDto1.getId());
        Assertions.assertThat(foundPrice.getProductId()).isEqualTo(priceDto1.getProductId());
    }
}
