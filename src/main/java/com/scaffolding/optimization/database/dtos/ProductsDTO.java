package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductsDTO {
    private Long id;

    private String name;

    private BigDecimal price;

    private Boolean deleted;

    private Long classificationId;

    private Long supplierId;

}
