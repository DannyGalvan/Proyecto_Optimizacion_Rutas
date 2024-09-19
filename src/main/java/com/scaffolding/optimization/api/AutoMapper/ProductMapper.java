package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Products;
import com.scaffolding.optimization.database.dtos.ProductsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Products, ProductsDTO> {
}
