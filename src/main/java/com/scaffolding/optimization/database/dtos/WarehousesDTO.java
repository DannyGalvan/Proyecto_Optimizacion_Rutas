package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class WarehousesDTO {
    private Long id;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Long supplierId;

}
