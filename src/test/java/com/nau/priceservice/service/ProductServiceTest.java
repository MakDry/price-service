package com.nau.priceservice.service;

import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.service.impl.ProductServiceImpl;
import com.nau.priceservice.util.dto.ProductDto;
import com.nau.priceservice.util.mappers.ProductMapper;
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
class ProductServiceTest {
//
//    @Mock
//    private ProductDaoImpl productDao;
//    @Mock
//    private ProductMapper productMapper;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//    @Test
//    void ProductService_GetAll_ReturnsTwoProductDto() {
//        ProductDto productDto1 = new ProductDto();
//        productDto1.setId("1");
//        ProductDto productDto2 = new ProductDto();
//        productDto2.setId("2");
//        ProductEntity productEntity1 = new ProductEntity();
//        productEntity1.setId("1");
//        ProductEntity productEntity2 = new ProductEntity();
//        productEntity2.setId("2");
//
//        when(productDao.findAll()).thenReturn(List.of(productEntity1, productEntity2));
//        when(productMapper.mapToDto(productEntity1)).thenReturn(productDto1);
//        when(productMapper.mapToDto(productEntity2)).thenReturn(productDto2);
//
//        List<ProductDto> products = productService.getAll();
//
//        Assertions.assertThat(products)
//                .isNotEmpty()
//                .hasSize(2);
//        Assertions.assertThat(products.get(0).getId()).isEqualTo(productDto1.getId());
//        Assertions.assertThat(products.get(1).getId()).isEqualTo(productDto2.getId());
//    }
//
//    @Test
//    void ProductService_Save_ReturnsSavedDto() throws InvalidProductException {
//        ProductDto productDto = new ProductDto();
//        productDto.setId("1");
//        productDto.setExternalId("101");
//        productDto.setTitle("Apples");
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setId("1");
//        productEntity.setExternalId("101");
//        productEntity.setTitle("Apples");
//
//        when(productDao.save(Mockito.any(ProductEntity.class))).thenReturn(Optional.of(productEntity));
//        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);
//        when(productMapper.mapFromDto(productDto)).thenReturn(productEntity);
//
//        Optional<ProductDto> savedProduct = productService.save(productDto);
//
//        Assertions.assertThat(savedProduct).isPresent();
//        Assertions.assertThat(savedProduct.get().getId())
//                .isNotEmpty()
//                .isNotEqualTo("")
//                .isEqualTo("1");
//    }
//
//    @Test
//    void ProductService_Update_ReturnsUpdatedDto() throws InvalidProductException {
//        ProductDto productDto = new ProductDto();
//        productDto.setId("1");
//        productDto.setExternalId("101");
//        productDto.setTitle("Apples");
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setId("1");
//        productEntity.setExternalId("101");
//        productEntity.setTitle("Apples");
//
//        when(productDao.isExists(Mockito.anyString())).thenReturn(true);
//        when(productDao.findById(Mockito.anyString())).thenReturn(List.of(productEntity));
//        when(productDao.update(Mockito.any(ProductEntity.class))).thenReturn(true);
//        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);
//
//        ProductDto updatedProduct = productService.update(productDto);
//
//        Assertions.assertThat(updatedProduct).isNotNull();
//        Assertions.assertThat(updatedProduct.getId()).isEqualTo(productDto.getId());
//    }
//
//    @Test
//    void ProductService_Delete_ReturnsDeletedDto() throws InvalidProductException {
//        ProductDto productDto = new ProductDto();
//        productDto.setId("1");
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setId("1");
//
//        when(productDao.isExists(Mockito.anyString())).thenReturn(true);
//        when(productDao.findById("1")).thenReturn(List.of(productEntity));
//        when(productDao.delete(productEntity)).thenReturn(true);
//        when(productMapper.mapToDto(productEntity)).thenReturn(productDto);
//
//        Optional<ProductDto> deletedProduct = productService.delete("1");
//
//        Assertions.assertThat(deletedProduct).isPresent();
//        Assertions.assertThat(deletedProduct.get().getId())
//                .isNotEmpty()
//                .isEqualTo("1");
//    }
//
//    @Test
//    void ProductService_IsExists_ReturnsTrueWhenObjectExists() {
//        String id = "1";
//
//        when(productDao.isExists(id)).thenReturn(true);
//
//        boolean isExistsResult = productService.isExists(id);
//
//        Assertions.assertThat(isExistsResult).isTrue();
//    }
//
//    @Test
//    void ProductService_IsExists_ReturnsFalseWhenObjectDoesntExists() {
//        String id = "1";
//
//        when(productDao.isExists(id)).thenReturn(false);
//
//        boolean isExistsResult = productService.isExists(id);
//
//        Assertions.assertThat(isExistsResult).isFalse();
//    }
}
