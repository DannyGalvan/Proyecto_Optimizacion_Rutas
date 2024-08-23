package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Drivers;
import com.scaffolding.optimization.database.dtos.DriversDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper extends GenericMapper<Drivers, DriversDTO> {
}
