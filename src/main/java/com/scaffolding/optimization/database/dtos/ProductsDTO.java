package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductsDTO {
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private Boolean deleted;

    @Min(value = 1, message = "Classification ID is required")
    private Long classificationId;

    @Min(value = 1, message = "Supplier ID is required")
    private Long supplierId;

}
