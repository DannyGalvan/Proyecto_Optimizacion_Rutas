package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassificationsDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean deleted;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<ProductsDTO> products;

}
