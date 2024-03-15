package com.nau.priceservice.module.product;

import com.nau.priceservice.module.product.impl.ProductQueryHandlerImpl;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryHandlerTest {
    @Mock
    private ProductService<ProductDto> productService;
    @InjectMocks
    private ProductQueryHandlerImpl productQueryHandler;

    @Test
    void ProductQueryHandler_getAll_ReturnsAllFoundDtos() {
        ProductDto productDto1 = new ProductDto();
        productDto1.setId("1");
        productDto1.setExternalId("101");
        productDto1.setTitle("Apples");
        ProductDto productDto2 = new ProductDto();
        productDto2.setId("2");
        productDto2.setExternalId("102");
        productDto2.setTitle("Pineapples");
        when(productService.getAll()).thenReturn(List.of(productDto1, productDto2));

        List<ProductDto> foundProducts = productQueryHandler.getAll();

        Assertions.assertThat(foundProducts)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void ProductQueryHandler_isExists_ReturnTrueIfDtoExists() {
        when(productService.isExists(Mockito.anyString())).thenReturn(true);

        boolean isExists = productQueryHandler.isExists("1");

        Assertions.assertThat(isExists).isTrue();
    }

    @Test
    void ProductQueryHandler_isExists_ReturnFalseIfDtoDoesntExists() {
        when(productService.isExists(Mockito.anyString())).thenReturn(false);

        boolean isExists = productQueryHandler.isExists("1");

        Assertions.assertThat(isExists).isFalse();
    }
}
