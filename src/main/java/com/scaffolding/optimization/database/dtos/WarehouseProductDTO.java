package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class WarehouseProductDTO {
    @Min(value = 1, message = "Warehouse ID is required")
    private Long warehouseId;

    @Min(value = 1, message = "Product ID is required")
    private Long productId;

    @Min(value = 1, message = "Stock is required")
    private Long partialStock;

}
