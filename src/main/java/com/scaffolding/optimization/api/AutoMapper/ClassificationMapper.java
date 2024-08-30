package com.scaffolding.optimization.api.AutoMapper;


import com.scaffolding.optimization.database.Entities.models.Classifications;
import com.scaffolding.optimization.database.dtos.ClassificationsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassificationMapper extends GenericMapper<Classifications, ClassificationsDTO> {
}
