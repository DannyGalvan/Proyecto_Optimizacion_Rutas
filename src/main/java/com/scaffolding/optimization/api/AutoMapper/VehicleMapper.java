package com.scaffolding.optimization.api.AutoMapper;


import com.scaffolding.optimization.database.Entities.models.Vehicles;
import com.scaffolding.optimization.database.dtos.VehiclesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper extends GenericMapper<Vehicles, VehiclesDTO> {
}
