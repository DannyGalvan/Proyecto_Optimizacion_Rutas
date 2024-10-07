package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Suppliers;
import com.scaffolding.optimization.database.dtos.SuppliersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SupplierMapper extends GenericMapper<Suppliers, SuppliersDTO> {
    @Mapping(target = "nit", source = "nit")
    Suppliers mapDtoToEntity(SuppliersDTO dto);

    @Mapping(target = "nit", source = "nit")
    SuppliersDTO mapEntityToDto(Suppliers entity);
}
