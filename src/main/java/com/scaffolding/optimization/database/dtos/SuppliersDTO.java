package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class SuppliersDTO {
    private Long id;

    private String name;

    private String phone;

    private String email;

    private Boolean deleted;

}
