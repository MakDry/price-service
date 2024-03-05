package com.nau.priceservice.service.impl;

import ch.qos.logback.classic.Logger;
import com.nau.priceservice.data.dao.interfaces.PriceDao;
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
    private PriceDao<PriceEntity, String> priceDao;
    private DtoMapper<PriceDto, PriceEntity> dtoMapper;

    @Autowired
    public PriceServiceImpl(PriceDao<PriceEntity, String> priceDao, DtoMapper<PriceDto, PriceEntity> dtoMapper) {
        this.priceDao = priceDao;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<PriceDto> getById(String id) {
        return priceDao.findById(id).stream()
                .map(priceEntity -> dtoMapper.mapToDto(priceEntity))
                .toList();
    }

    @Override
    public List<PriceDto> getAll() {
        return priceDao.findAll().stream()
                .map(priceEntity -> dtoMapper.mapToDto(priceEntity))
                .toList();
    }

    @Override
    public Optional<PriceDto> save(PriceDto priceDto) throws InvalidPriceException {
        if (priceDto.getCurrency() == null || priceDto.getUnitAmount() == 0 || priceDto.getUnitAmountDecimal() == 0.0
            || priceDto.getPurchasePrice() == 0 || priceDto.getSuggestedAmount() == 0) {
            logger.error("In class {} was send entity without initialized fields, to save(): {}",
                    PriceServiceImpl.class.getSimpleName(), priceDto);
            throw new InvalidPriceException();
        }
        PriceEntity savedPrice = priceDao.save(dtoMapper.mapFromDto(priceDto)).get();
        return Optional.of(dtoMapper.mapToDto(savedPrice));
    }

    @Override
    public boolean update(PriceDto priceDto) throws InvalidPriceException {
        if (priceDto.getId().equals("") || priceDto.getCurrency() == null || priceDto.getUnitAmount() == 0
                || priceDto.getUnitAmountDecimal() == 0.0 || priceDto.getPurchasePrice() == 0
                || priceDto.getSuggestedAmount() == 0) {
            logger.error("In class {} was send entity without initialized fields, to update(): {}",
                    PriceServiceImpl.class.getSimpleName(), priceDto);
            throw new InvalidPriceException();
        }

        PriceEntity updatedPrice = dtoMapper.mapFromDto(priceDto);

        if (priceDao.findById(priceDto.getId()).isEmpty()) {
            updatedPrice.setId("");
            priceDao.save(updatedPrice);
            return true;
        } else {
            if (priceDao.update(updatedPrice)) {
                return true;
            } else {
                logger.warn("In class {} method update() couldn't update next entity properly: {}",
                        PriceServiceImpl.class.getSimpleName(), updatedPrice);
                throw new InvalidPriceException();
            }
        }
    }

    @Override
    public Optional<PriceDto> delete(String id) throws InvalidPriceException {
        PriceEntity foundEntity = priceDao.findById(id).get(0);

        if (foundEntity == null) {
            logger.warn("In class {} method delete() couldn't find any entity with id: {}",
                    PriceServiceImpl.class.getSimpleName(), id);
            throw new InvalidPriceException();
        } else {
            priceDao.delete(foundEntity);
        }
        return Optional.ofNullable(dtoMapper.mapToDto(foundEntity));
    }
}
