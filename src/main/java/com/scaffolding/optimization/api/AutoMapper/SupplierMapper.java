package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Suppliers;
import com.scaffolding.optimization.database.dtos.SuppliersDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SupplierMapper extends GenericMapper<Suppliers, SuppliersDTO> {
}
