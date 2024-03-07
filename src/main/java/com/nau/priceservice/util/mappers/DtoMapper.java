package com.nau.priceservice.util.mappers;

public interface DtoMapper<T, K> {
    T mapToDto(K k);

    K mapFromDto(T t);
}
