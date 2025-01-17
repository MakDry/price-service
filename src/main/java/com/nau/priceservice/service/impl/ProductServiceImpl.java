package com.nau.priceservice.service.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.data.dao.interfaces.ProductRepository;
import com.nau.priceservice.data.entity.ProductEntity;
import com.nau.priceservice.exceptions.product.InvalidProductException;
import com.nau.priceservice.service.interfaces.ProductService;
import com.nau.priceservice.util.dto.ProductDto;
import com.nau.priceservice.util.mappers.DtoMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService<ProductDto> {

    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
    private ProductRepository productDao;
    private DtoMapper<ProductDto, ProductEntity> dtoMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productDao, DtoMapper<ProductDto, ProductEntity> dtoMapper) {
        this.productDao = productDao;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<ProductDto> getAll() {
        return productDao.findAll().stream()
                .map(productEntity -> dtoMapper.mapToDto(productEntity))
                .toList();
    }

    @Override
    public Optional<ProductDto> save(ProductDto productDto) throws InvalidProductException {
        if (productDto.getExternalId().equals("") || productDto.getTitle().equals("")) {
            logger.error("In class {} was send entity without initialized fields, to save(): {}",
                    ProductServiceImpl.class.getSimpleName(), productDto);
            throw new InvalidProductException("Not all ProductDto fields have been filled in to save object");
        }
        ProductEntity savedProduct = productDao.save(dtoMapper.mapFromDto(productDto));
        return Optional.of(dtoMapper.mapToDto(savedProduct));
    }

    @Override
    public ProductDto update(ProductDto productDto) throws InvalidProductException {
        if (productDto.getId().equals("") || productDto.getTitle().equals("")) {
            logger.error("In class {} was send entity without initialized fields, to update(): {}",
                    ProductServiceImpl.class.getSimpleName(), productDto);
            throw new InvalidProductException("Not all ProductDto fields have been filled in to update object");
        }

        if (!productDao.existsById(productDto.getId())) {
            logger.warn("In class {} in method update() wasn't found any entities with id: {}",
                    ProductServiceImpl.class.getSimpleName(), productDto.getId());
            throw new InvalidProductException("No suitable Product entity was found to update");
        } else {
            ProductEntity productToUpdate = productDao.findById(productDto.getId()).get();
            productToUpdate.setTitle(productDto.getTitle());
            return dtoMapper.mapToDto(productDao.save(productToUpdate));
        }
    }

    @Override
    public Optional<ProductDto> delete(String id) throws InvalidProductException {
        if (!productDao.existsById(id)) {
            logger.warn("In class {} method delete() couldn't find any entity with id: {}",
                    ProductServiceImpl.class.getSimpleName(), id);
            throw new InvalidProductException("No suitable Product entity was found to delete");
        }

        ProductEntity productToDelete = productDao.findById(id).get();
        productDao.delete(productToDelete);
        return Optional.of(dtoMapper.mapToDto(productToDelete));
    }

    @Override
    public boolean isExists(String id) {
        return productDao.existsById(id);
    }
}
