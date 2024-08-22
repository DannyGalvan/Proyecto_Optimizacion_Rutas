package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class DriversDTO {
    private Long id;

    private String name;

    private String phone;

    private Boolean deleted;

    private Long scheduleId;

    private Long userId;

}
