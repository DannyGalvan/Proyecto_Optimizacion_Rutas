package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Gastypes;
import com.scaffolding.optimization.database.dtos.GastypesDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GasTypeMapper extends GenericMapper<Gastypes, GastypesDTO> {

}
