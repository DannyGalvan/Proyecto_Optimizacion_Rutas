package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class WarehouseProductDTO {
    private Long warehouseId;

    private Long productId;

    private Long partialStock;

}
