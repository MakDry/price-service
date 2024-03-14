package com.nau.priceservice.service;

import com.nau.priceservice.data.dao.interfaces.ProductRepository;
import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.service.impl.ProductServiceImpl;
import com.nau.priceservice.util.dto.ProductDto;
import com.nau.priceservice.util.mappers.ProductMapper;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productDao;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

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
    void ProductService_getAll_ReturnsTwoProductDto() {
        when(productDao.findAll()).thenReturn(List.of(productEntity, productEntity));
        when(productMapper.mapToDto(Mockito.any(ProductEntity.class))).thenReturn(productDto);

        List<ProductDto> products = productService.getAll();

        Assertions.assertThat(products)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void ProductService_save_ReturnsSavedDto() throws InvalidProductException {
        when(productDao.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);
        when(productMapper.mapFromDto(productDto)).thenReturn(productEntity);

        Optional<ProductDto> savedProduct = productService.save(productDto);

        Assertions.assertThat(savedProduct).isPresent();
        Assertions.assertThat(savedProduct.get().getId())
                .isNotEmpty()
                .isNotEqualTo("")
                .isEqualTo("1");
    }

    @Test
    void ProductService_save_DtoWithoutExternalIdReturnsException() {
        productDto.setExternalId("");
        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.save(productDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all ProductDto fields have been filled in to save object");
    }

    @Test
    void ProductService_save_DtoWithoutTitleReturnsException() {
        productDto.setTitle("");
        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.save(productDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all ProductDto fields have been filled in to save object");
    }

    @Test
    void ProductService_update_ReturnsUpdatedDto() throws InvalidProductException {
        when(productDao.existsById(Mockito.anyString())).thenReturn(true);
        when(productDao.findById(Mockito.anyString())).thenReturn(Optional.of(productEntity));
        when(productDao.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);

        ProductDto updatedProduct = productService.update(productDto);

        Assertions.assertThat(updatedProduct).isNotNull();
        Assertions.assertThat(updatedProduct.getId()).isEqualTo(productDto.getId());
    }

    @Test
    void ProductService_update_DtoWithoutIdReturnsException() {
        productDto.setId("");
        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.update(productDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all ProductDto fields have been filled in to update object");
    }

    @Test
    void ProductService_update_DtoWithoutTitleReturnsException() {
        productDto.setTitle("");
        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.update(productDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("Not all ProductDto fields have been filled in to update object");
    }

    @Test
    void ProductService_update_DtoWithNonexistentIdReturnsException() {
        when(productDao.existsById(Mockito.anyString())).thenReturn(false);

        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.update(productDto);
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("No suitable Product entity was found to update");
    }

    @Test
    void ProductService_delete_ReturnsDeletedDto() throws InvalidProductException {
        when(productDao.existsById(Mockito.anyString())).thenReturn(true);
        when(productDao.findById("1")).thenReturn(Optional.of(productEntity));
        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);

        Optional<ProductDto> deletedProduct = productService.delete("1");

        Assertions.assertThat(deletedProduct).isPresent();
        Assertions.assertThat(deletedProduct.get().getId())
                .isNotEmpty()
                .isEqualTo("1");
    }

    @Test
    void ProductService_delete_DtoWithNonexistentIdReturnsException() {
        when(productDao.existsById(Mockito.anyString())).thenReturn(false);

        InvalidProductException e = org.junit.jupiter.api.Assertions.assertThrows(InvalidProductException.class, () -> {
            productService.delete(productDto.getId());
        });
        Assertions.assertThat(e.getMessage()).isEqualTo("No suitable Product entity was found to delete");
    }

    @Test
    void ProductService_isExists_ReturnsTrueWhenObjectExists() {
        String id = "1";

        when(productDao.existsById(id)).thenReturn(true);

        boolean isExistsResult = productService.isExists(id);

        Assertions.assertThat(isExistsResult).isTrue();
    }

    @Test
    void ProductService_isExists_ReturnsFalseWhenObjectDoesntExists() {
        String id = "1";

        when(productDao.existsById(id)).thenReturn(false);

        boolean isExistsResult = productService.isExists(id);

        Assertions.assertThat(isExistsResult).isFalse();
    }
}
