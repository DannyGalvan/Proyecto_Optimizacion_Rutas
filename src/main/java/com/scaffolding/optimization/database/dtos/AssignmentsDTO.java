package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class AssignmentsDTO {
    private Long id;

    private Boolean deleted;

    private Long vehicleId;

    private Long driverId;

    private Long statusId;

}
