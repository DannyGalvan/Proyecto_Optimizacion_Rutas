package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class RolesDTO {
    private Long id;

    private String name;

    private String description;

    private Boolean deleted;

}
