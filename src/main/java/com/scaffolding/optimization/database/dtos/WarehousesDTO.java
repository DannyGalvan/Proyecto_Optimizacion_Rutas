package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WarehousesDTO {
    private Long id;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @DecimalMin(value = "0.0", inclusive = false, message = "Longitude must be greater than 0")
    private String longitude;

    @DecimalMin(value = "0.0", inclusive = false, message = "Latitude must be greater than 0")
    private String latitude;

   @Min(value = 1, message = "Supplier ID is required")
    private Long supplierId;

}
