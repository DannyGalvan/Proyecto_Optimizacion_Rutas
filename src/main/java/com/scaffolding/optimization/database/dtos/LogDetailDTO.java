package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class LogDetailDTO {
    private Long id;

    private Long headerId;

    private String fieldName;

    private String oldValue;

    private String newValue;

}
