package com.nau.priceservice.module.product;

import com.nau.priceservice.exceptions.InvalidDtoException;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.module.product.impl.ProductCommandHandlerImpl;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
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
class ProductCommandHandlerTest {
    @Mock
    private ProductService<ProductDto> productService;
    @InjectMocks
    private ProductCommandHandlerImpl productCommandHandler;

    private ProductDto productDto;
    private ProductCommand productCommand;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setId("1");
        productDto.setExternalId("101");
        productDto.setTitle("Apples");
        productCommand = new ProductCommand();
        productCommand.setExternalId("101");
        productCommand.setTitle("Apples");
    }

    @Test
    void ProductCommandHandler_handleCreate_ReturnsSavedDtoWithSameFields() throws InvalidProductException {
        when(productService.save(Mockito.any(ProductDto.class))).thenReturn(Optional.of(productDto));

        ProductDto savedProduct = productCommandHandler.handleCreate(productCommand);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isNotEmpty();
        Assertions.assertThat(savedProduct.getExternalId()).isEqualTo(productCommand.getExternalId());
        Assertions.assertThat(savedProduct.getTitle()).isEqualTo(productCommand.getTitle());
    }

    @Test
    void ProductCommandHandler_handleUpdate_ReturnsDtoWithUpdatedFields() throws InvalidProductException {
        when(productService.update(Mockito.any(ProductDto.class))).thenReturn(productDto);

        ProductDto updatedProduct = productCommandHandler.handleUpdate(productDto.getId(), productCommand);

        Assertions.assertThat(updatedProduct).isNotNull();
        Assertions.assertThat(updatedProduct.getId()).isEqualTo(productDto.getId());
        Assertions.assertThat(updatedProduct.getExternalId()).isEqualTo(productDto.getExternalId());
        Assertions.assertThat(updatedProduct.getTitle()).isEqualTo(productCommand.getTitle());
    }

    @Test
    void ProductCommandHandler_handleDelete_ReturnsIdAndExternalIdOfDeletedDtoInQuery() throws InvalidDtoException {
        String id = "1";
        productDto.setId(id);

        when(productService.delete(id)).thenReturn(Optional.of(productDto));

        ProductQuery deletedProductQuery = productCommandHandler.handleDelete(productDto.getId());

        Assertions.assertThat(deletedProductQuery).isNotNull();
        Assertions.assertThat(deletedProductQuery.getId()).isEqualTo(productDto.getId());
        Assertions.assertThat(deletedProductQuery.getExternalId()).isEqualTo(productDto.getExternalId());
    }
}
