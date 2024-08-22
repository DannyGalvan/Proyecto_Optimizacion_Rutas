package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class ModulesDTO {
    private Long id;

    private String name;

    private String description;

    private String path;

    private String icon;

    private Boolean deleted;

}
