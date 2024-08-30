package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehiclesDTO {
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal maxWeight;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal minWeight;

    @NotEmpty(message = "Name cannot be empty")
    private String description;

    @NotEmpty(message = "Name cannot be empty")
    private String licensePlate;

    @Min(value = 1, message = "should not be less than 1")
    private Long stopLimit;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal activationCost;

    private Boolean deleted;

    @Min(value = 1, message = "Gas Type ID is required")
    private Long gasTypeId;

}
