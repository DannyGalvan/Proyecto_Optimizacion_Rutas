package com.scaffolding.optimization.database.dtos;


import com.opencsv.bean.CsvBindByName;
import com.scaffolding.optimization.api.validator.UniqueValue;
import com.scaffolding.optimization.database.Entities.models.Suppliers;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SuppliersDTO {
    private Long id;

    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name is required")
    @CsvBindByName
    private String name;

    @NotEmpty(message = "Address is required")
    @NotNull(message = "Address is required")
    @UniqueValue(fieldName = "phone", entityClass = Suppliers.class, message = "Phone number already in use")
    @CsvBindByName
    private String phone;

    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    @UniqueValue(fieldName = "email", entityClass = Suppliers.class, message = "Email already in use")
    @CsvBindByName
    private String email;

    @CsvBindByName
    private Boolean deleted;

}
