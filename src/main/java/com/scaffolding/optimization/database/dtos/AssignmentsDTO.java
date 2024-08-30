package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentsDTO {
    private Long id;

    private Boolean deleted;

    @Min(value = 1, message = "Vehicle ID is required")
    private Long vehicleId;

    @Min(value = 1, message = "Driver ID is required")
    private Long driverId;

    @Min(value = 1, message = "status  ID is required")
    private Long statusId;

}
