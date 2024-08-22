package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class GastypesDTO {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private Boolean deleted;

}
