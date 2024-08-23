package com.scaffolding.optimization.api.AutoMapper;



import com.scaffolding.optimization.database.Entities.models.Customers;
import com.scaffolding.optimization.database.dtos.CustomersDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper extends GenericMapper<Customers, CustomersDTO> {

}
