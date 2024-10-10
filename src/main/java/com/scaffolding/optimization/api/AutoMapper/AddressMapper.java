package com.scaffolding.optimization.api.AutoMapper;


import com.scaffolding.optimization.database.Entities.models.Addresses;
import com.scaffolding.optimization.database.dtos.AddressesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends GenericMapper<Addresses, AddressesDTO> {
}
