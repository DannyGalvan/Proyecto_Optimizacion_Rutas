package com.scaffolding.optimization.api.AutoMapper;


public interface GenericMapper<E, D> {
    E mapDtoToEntity(D dto);
    D mapEntityToDto(E entity);
}
