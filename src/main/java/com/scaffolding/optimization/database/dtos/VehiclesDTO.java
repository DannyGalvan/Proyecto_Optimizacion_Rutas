package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehiclesDTO {
    private Long id;

    private BigDecimal maxWeight;

    private BigDecimal minWeight;

    private String description;

    private String licensePlate;

    private Long stopLimit;

    private BigDecimal activationCost;

    private Boolean deleted;

    private Long gasTypeId;

}
