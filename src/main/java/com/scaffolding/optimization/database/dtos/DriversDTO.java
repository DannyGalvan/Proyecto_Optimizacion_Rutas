package com.scaffolding.optimization.database.dtos;


import com.scaffolding.optimization.api.validator.UniqueValue;
import com.scaffolding.optimization.database.Entities.models.Drivers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DriversDTO {
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Phone cannot be empty")
    @UniqueValue(fieldName = "phone", entityClass = Drivers.class, message = "Phone number already in use")
    private String phone;

    private Boolean deleted;

    @Min(value = 1, message = "schedule ID is required")
    private Long scheduleId;

    @NotNull(message = "User cannot be null")
    @Valid
    private UsersDTO user;

}
