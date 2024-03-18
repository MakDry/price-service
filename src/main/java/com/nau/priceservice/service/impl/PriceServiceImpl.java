package com.nau.priceservice.service.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.data.dao.interfaces.PriceRepository;
import com.nau.priceservice.data.entity.PriceEntity;
import com.nau.priceservice.exceptions.price.InvalidPriceException;
import com.nau.priceservice.service.interfaces.PriceService;
import com.nau.priceservice.util.dto.PriceDto;
import com.nau.priceservice.util.mappers.DtoMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("priceService")
@Transactional
public class PriceServiceImpl implements PriceService<PriceDto> {

    private static final Logger logger =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
    private PriceRepository priceDao;
    private DtoMapper<PriceDto, PriceEntity> dtoMapper;

    @Autowired
    public PriceServiceImpl(PriceRepository priceDao, DtoMapper<PriceDto, PriceEntity> dtoMapper) {
        this.priceDao = priceDao;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<PriceDto> getAll() {
        return priceDao.findAll().stream()
                .map(priceEntity -> dtoMapper.mapToDto(priceEntity))
                .toList();
    }

    @Override
    public Optional<PriceDto> save(PriceDto priceDto) throws InvalidPriceException {
        if (priceDto.getProductId().equals("") || priceDto.getCurrency().equals("") || priceDto.getUnitAmount() == 0
                || priceDto.getUnitAmountDecimal() == 0.0 || priceDto.getPurchasePrice() == 0
                || priceDto.getSuggestedAmount() == 0) {
            logger.error("In class {} was send entity without initialized fields, to save(): {}",
                    PriceServiceImpl.class.getSimpleName(), priceDto);
            throw new InvalidPriceException("Not all PriceDto fields have been filled in to save object");
        }
        PriceEntity savedPrice = priceDao.save(dtoMapper.mapFromDto(priceDto));
        return Optional.of(dtoMapper.mapToDto(savedPrice));
    }

    @Override
    public PriceDto update(PriceDto priceDto) throws InvalidPriceException {
        if (priceDto.getId().equals("") || priceDto.getCurrency().equals("") || priceDto.getUnitAmount() == 0
                || priceDto.getUnitAmountDecimal() == 0.0 || priceDto.getPurchasePrice() == 0
                || priceDto.getSuggestedAmount() == 0) {
            logger.error("In class {} was send entity without initialized fields, to update(): {}",
                    PriceServiceImpl.class.getSimpleName(), priceDto);
            throw new InvalidPriceException("Not all PriceDto fields have been filled in to update object");
        }

        PriceEntity priceToUpdate = dtoMapper.mapFromDto(priceDto);

        if (!priceDao.existsById(priceDto.getId())) {
            logger.warn("In class {} in method update() wasn't found any entities with id: {}",
                    PriceServiceImpl.class.getSimpleName(), priceDto.getId());
            throw new InvalidPriceException("No suitable Price entity was found to update");
        } else {
            priceToUpdate.setProductId(priceDao.findById(priceDto.getId()).get().getProductId());
            return dtoMapper.mapToDto(priceDao.save(priceToUpdate));
        }
    }

    @Override
    public Optional<PriceDto> delete(String id) throws InvalidPriceException {
        Optional<PriceEntity> priceToDelete = priceDao.findById(id);

        if (priceToDelete.isEmpty()) {
            logger.warn("In class {} method delete() couldn't find any entity with id: {}",
                    PriceServiceImpl.class.getSimpleName(), id);
            throw new InvalidPriceException("No suitable Price entity was found to delete");
        } else {
            priceDao.delete(priceToDelete.get());
        }
        return Optional.of(dtoMapper.mapToDto(priceToDelete.get()));
    }

    @Override
    public List<PriceDto> getAllPricesOfProduct(String productId) {
        return priceDao.findAllByProductId(productId).stream()
                .map(priceEntity -> dtoMapper.mapToDto(priceEntity))
                .toList();
    }

    @Override
    public Optional<PriceDto> getOnePriceOfProduct(String id, String productId) throws InvalidPriceException {
        return Optional.ofNullable(dtoMapper.mapToDto(priceDao.findByIdAndProductId(id, productId)
                .orElseThrow(InvalidPriceException::new)));
    }
}
